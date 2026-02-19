package com.emergencia.prontosocorro.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;



@Service
public class CareService {

    private final RegretsMedicService regretsMedicService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryFirstCare repositoryFirstCare;
    private final CIDClassifierService cidClassifierService;
    private RepositoryCIDKeywordRule repositoryCIDKeywordRule;

    public CareService(
            RegretsMedicService regretsMedicService,
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryCIDKeywordRule repositoryCIDKeywordRule,
            CIDClassifierService cidClassifierService
        ) {
        this.regretsMedicService = regretsMedicService;
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
        this.repositoryCIDKeywordRule = repositoryCIDKeywordRule;
        this.cidClassifierService = cidClassifierService;
    }


    public void defineCID(FirstCare firstCare, CID cid){
        if(firstCare == null || cid == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }

           firstCare.setCid(cid);
            SeverityLevel severity = cid.getSeverityLevel();

            
            People people = firstCare.getPeople();

            people.setSeverity(severity);
            people.setStatusPatient(mapSeverityToStatus(severity));

            applyProcedures(firstCare, null);

            repositoryFirstCare.save(firstCare);
            repositoryPeople.save(people);
    }

    public SpecialistMedic defineSpecialistMedic(String description) {
        List<CIDKeywordRule> rules = repositoryCIDKeywordRule.findAll();

        String desc = description.toLowerCase();
        
        for (CIDKeywordRule rule : rules) {

            if (desc.contains(rule.getKeyword().toLowerCase())) {

               if(rule.getSpecialistMedic() != null) {
                return rule.getSpecialistMedic();
               }
            }
        }

        return SpecialistMedic.CLINICAL_MEDICINE;
    }

    public StatusType mapSeverityToStatus(SeverityLevel severity) {
      
        return switch (severity) {
            case GRAVE -> StatusType.CRITICO;
            case MODERADO -> StatusType.URGENTE;
            case LEVE -> StatusType.ENFERMO;
            case UTI -> StatusType.INTERNADO;
        };
    }

    public FirstCare createFirstCare(People people, Hospital hospital) {
        // Lógica para criar um atendimento inicial
        if (people == null || hospital == null) {
            throw new IllegalArgumentException("People and Hospital must not be null");
        }

        if (people.getStatusPatient() == null) {
            throw new IllegalArgumentException("StatePatient must not be null");
        }

        StatusType patientStatus = people.getStatusPatient();
        if (patientStatus == StatusType.MORTO) {
            throw new IllegalStateException("Patient is not in a state to receive care");
        }

        if (patientStatus == StatusType.MORTO) {
            throw new IllegalStateException(
                    "Dead people do not receive hospital care");
        }

        List<ComorbidityType> comorbidities = people.getComorbidities();
        if (comorbidities == null || comorbidities.isEmpty()) {
            people.setComorbidities(new ArrayList<>());
        } else {
            people.setComorbidities(comorbidities);
        }

        People savedPeople = repositoryPeople.save(people);

        CID cid = cidClassifierService.classify(savedPeople.getDescription());

        if (cid != null) {
            SeverityLevel severity = cid.getSeverityLevel();
            savedPeople.setSeverity(severity);
            savedPeople.setStatusPatient(mapSeverityToStatus(severity));
        }

        System.out.println("CID detectado: " + (cid != null ? cid.getCode() : "NULL"));

        Object medicResult = regretsMedicService.defineSepSpecialistMedic(savedPeople);
        if (!(medicResult instanceof SpecialistMedic)) {
            throw new IllegalStateException("Expected SpecialistMedic from defineSepSpecialistMedic");
        }
        SpecialistMedic specialistMedic = (SpecialistMedic) medicResult;

        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(savedPeople);
        firstCare.setHospital(hospital);
        firstCare.setCid(cid);
        firstCare.setSpecialistMedic(specialistMedic);
        firstCare.setComorbidities(savedPeople.getComorbidities() != null ? new HashSet<>(savedPeople.getComorbidities()) : new HashSet<>());
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        return repositoryFirstCare.save(firstCare);
    }

    public void dischargePatient(FirstCare firstCare) {
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        firstCare.disCharge();
    }

    public void applyProcedures(FirstCare firstCare, Set<CareofPacients> proceduresToAdd) {
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }

        CID cid = firstCare.getCid();
        if (cid == null) {
            throw new IllegalArgumentException("CID must not be null");
        }


        String groupCode = firstCare.getCid().getCode();
        if (groupCode.length() > 3) {
            groupCode = groupCode.substring(0, 3);
        }


    }

    private boolean isCriticalCare(FirstCare fc) {
        return fc.getCareStatus() == CareStatus.EM_CIRURGIA
            || fc.getCareStatus() == CareStatus.AGUARDANDO_ATENDIMENTO;
    }

    private boolean isSevereCase(People p) {
        return p.getSeverity() == SeverityLevel.GRAVE;
    }

    private boolean hasProcedures(FirstCare fc) {
        return !fc.getProcedures().isEmpty();
    }

    public boolean canBeDiscarged(People people, FirstCare firstCare) {
        boolean isGrave = people.getSeverity() == SeverityLevel.GRAVE;
        if (isGrave) {
            return false;
        }

        CareStatus careStatus = firstCare.getCareStatus();

        if(careStatus == CareStatus.EM_CIRURGIA ||
        careStatus == CareStatus.AGUARDANDO_ATENDIMENTO ||
        careStatus == CareStatus.EM_ATENDIMENTO ||
        careStatus == CareStatus.EM_OBSERVACAO) {
          if (isCriticalCare(firstCare) || isSevereCase(people) || !hasProcedures(firstCare)) return false;
        }

        return !firstCare.getProcedures().isEmpty();
    }

}
