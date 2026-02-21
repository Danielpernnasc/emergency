package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Controller.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.Controller.DTO.Request.PeopleRequest;
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
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

@Service
public class CareService {

    private final RepositoryPeople repositoryPeople;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryCID repositoryCID;
    private final RepositoryCIDKeywordRule repositoryCIDKeywordRule;

    public CareService(
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryCIDKeywordRule repositoryCIDKeywordRule,
            RepositoryCID repositoryCID
        ) {
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
        this.repositoryCIDKeywordRule = repositoryCIDKeywordRule;
        this.repositoryCID = repositoryCID;


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

    public CareStatus mapStatusTypeToCareStatus(StatusType statusType) {
        return switch (statusType) {
            case ENFERMO -> CareStatus.AGUARDANDO_ATENDIMENTO;
            case URGENTE -> CareStatus.EM_ATENDIMENTO;
            case CRITICO -> CareStatus.EM_CIRURGIA;
            case INTERNADO -> CareStatus.EM_OBSERVACAO;
            case MORTO -> CareStatus.OBITO;
            default -> throw new IllegalArgumentException("Unexpected value: " + statusType);
        };
    }
    public StatusType mapSeverityToStatus(SeverityLevel severity) {
      
        return switch (severity) {
            case GRAVE -> StatusType.CRITICO;
            case MODERADO -> StatusType.URGENTE;
            case LEVE -> StatusType.ENFERMO;
            case UTI -> StatusType.INTERNADO;
        };
    }

    public FirstCare createFirstCare(People people, Hospital hospital, FirstCareRequest req) {
        // Lógica para criar um atendimento inicial
        if (people == null || hospital == null) {
            throw new IllegalArgumentException("People and Hospital must not be null");
        }

        if (people.getStatusPatient() == null) {
            throw new IllegalArgumentException("StatePatient must not be null");
        }


       

        List<ComorbidityType> comorbidities = people.getComorbidities();
        if (comorbidities == null || comorbidities.isEmpty()) {
            people.setComorbidities(new ArrayList<>());
        } else {
            people.setComorbidities(comorbidities);
        }

        people.ensureAlive();

        CID cid = null;

        if (req.cidCode() != null) {

            cid = repositoryCID.findById(req.cidCode())
                    .orElseThrow(() -> new RuntimeException("CID not found"));

            people.setSeverity(cid.getSeverityLevel());
            people.changeStatus(cid.getSeverityLevel());
        }

        repositoryPeople.save(people);

        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);
        firstCare.setCid(cid);
        firstCare.setSpecialistMedic(req.specialistMedic());
        firstCare.setCareStatus(req.careStatus());

        return repositoryFirstCare.save(firstCare);
        }

    public void dischargePatient(FirstCare firstCare) {
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        firstCare.disCharge();
    }


    public void applyProcedures(Long id, FirstCare firstCare, Set<CareofPacients> proceduresToAdd, CareStatus newStatus) {
        
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }

         if (proceduresToAdd != null) {
            firstCare.getProcedures().addAll(proceduresToAdd);
        }

        if (newStatus != null) {
            firstCare.setCareStatus(newStatus);
        }

         firstCare.getPeople().ensureAlive();
        
        repositoryFirstCare.save(firstCare);
    }

    private StatusType mapCareStatusToStatusType(CareStatus careStatus) {
        return switch (careStatus) {
            case AGUARDANDO_ATENDIMENTO -> StatusType.ENFERMO;
            case EM_ATENDIMENTO -> StatusType.URGENTE;
            case EM_CIRURGIA -> StatusType.CRITICO;
            case EM_OBSERVACAO -> StatusType.URGENTE;
            case ALTA -> StatusType.ENFERMO;
            case OBITO -> StatusType.MORTO;
        };
    }

    private boolean isCriticalCare(FirstCare fc) {
        return fc.getCareStatus() == CareStatus.EM_CIRURGIA;
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
          if (isCriticalCare(firstCare) || isSevereCase(people) || !hasProcedures(firstCare)) {
              return false;
          }
        }else if(careStatus == CareStatus.ALTA || careStatus == CareStatus.OBITO) {
            return true;
        }

        return !firstCare.getProcedures().isEmpty();
    }

    public void registerDeath(FirstCare firstCare, String cause, LocalDateTime deathTime) {
        firstCare.getPeople().ensureAlive();
        People people = firstCare.getPeople();
        if(people.getStatusPatient() == StatusType.MORTO) {
            throw new IllegalStateException("Patient already dead");
        }

        people.registerDeath(cause, deathTime);

        firstCare.setCareStatus(CareStatus.OBITO);
           repositoryPeople.save(people);
        repositoryFirstCare.save(firstCare);
    }
}
