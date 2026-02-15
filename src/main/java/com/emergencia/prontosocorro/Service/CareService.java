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
import com.emergencia.prontosocorro.Domain.Entity.CIDProceduresRules;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryproceduresRules;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;

@Service
public class CareService {

    private final RegretsMedicService regretsMedicService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryHospital repositoryHospital;
    private final RepositoryproceduresRules repositoryProceduresRules;  

    public CareService(
            RegretsMedicService regretsMedicService,
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryHospital repositoryHospital,
            RepositoryproceduresRules repositoryProceduresRules
        ) {
        this.regretsMedicService = regretsMedicService;
        this.repositoryHospital = repositoryHospital;
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
        this.repositoryProceduresRules = repositoryProceduresRules;
    }

    public FirstCare createFirstCare(People people, Hospital hospital) {
        // Lógica para criar um atendimento inicial
        if (people == null || hospital == null) {
            throw new IllegalArgumentException("People and Hospital must not be null");
        }

        if (people.getStatePatient() == null) {
            throw new IllegalArgumentException("StatePatient must not be null");
        }

        if (!people.getStatePatient().canReceiveCare()) {
            throw new IllegalStateException("Patient is not in a state to receive care");
        }

        if (people.getStatePatient().getStatusType() == StatusType.MORTO) {
            throw new IllegalStateException(
                    "Dead people do not receive hospital care");
        }

        // 🔹 garante que o paciente existe no banco

        List<ComorbidityType> comorbidities = people.getComorbidities();
        if (comorbidities == null || comorbidities.isEmpty()) {
            people.setComorbidities(new ArrayList<>());
        } else {
            people.setComorbidities(comorbidities);
        }

        People savedPeople = repositoryPeople.save(people);

        Object medicResult = regretsMedicService.defineSepSpecialistMedic(savedPeople);
        if (!(medicResult instanceof SpecialistMedic)) {
            throw new IllegalStateException("Expected SpecialistMedic from defineSepSpecialistMedic");
        }
        SpecialistMedic specialistMedic = (SpecialistMedic) medicResult;
        CID cid = new CID(null, null, null, specialistMedic);
        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(savedPeople);
        firstCare.setHospital(hospital);
        firstCare.setSpecialistMedic(specialistMedic);
        firstCare.setComorbidities(savedPeople.getComorbidities() != null ? new HashSet<>(savedPeople.getComorbidities()) : new HashSet<>());
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        return firstCare;
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


        String groupCode = firstCare.getCid().getGroup().getCode();

        Iterable<CareofPacients> rules = repositoryProceduresRules.findByGroup_Code(groupCode);

        Set<CareofPacients> allowedProcedures = new HashSet<>();
        rules.forEach(allowedProcedures::add);
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
