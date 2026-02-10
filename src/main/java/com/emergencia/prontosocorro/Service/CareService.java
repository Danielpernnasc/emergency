package com.emergencia.prontosocorro.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;

@Service
public class CareService {

    private final RegretsMedicService regretsMedicService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryHospital repositoryHospital;

    public CareService(
            RegretsMedicService regretsMedicService,
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryHospital repositoryHospital) {
        this.regretsMedicService = regretsMedicService;
        this.repositoryHospital = repositoryHospital;
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
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
        FirstCare firstCare = new FirstCare(
            savedPeople,
            hospital,
            specialistMedic,
            comorbidities != null ? new HashSet<>(comorbidities) : new HashSet<>(),
            CareStatus.EM_ATENDIMENTO
        );
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
        Set<CareofPacients> procedures = firstCare.getProcedures();
        String desc = firstCare.getPeople().getDescription().toLowerCase();

        if (desc.contains("febre")) {
            procedures.add(CareofPacients.MEDICACAO);

        } else if (desc.contains("corte")) {
            procedures.add(CareofPacients.CURATIVO);
            procedures.add(CareofPacients.SUTURA);

        } else if (desc.contains("fratura")) {
            procedures.add(CareofPacients.IMOBILIZACAO);
        } else if (desc.contains("trauma perfurante")
                || desc.contains("infarto")
                || desc.contains("derrame")
                || desc.contains("acidente vascular cerebral")) {
            procedures.add(CareofPacients.CINTILOGRAFIA);
            procedures.add(CareofPacients.EXAMES_IMAGEM);
            procedures.add(CareofPacients.CIRURGIA);
            procedures.add(CareofPacients.MEDICACAO);
            procedures.add(CareofPacients.OBSERVACAO);
        } else if (desc.contains("dificuldade respiratória") || desc.contains("falta de ar")) {
            procedures.add(CareofPacients.OXIGENIOTERAPIA);
        } else if (desc.contains("dor no peito") || desc.contains("infarto") || desc.contains("Trauma perfurante")
                || desc.contains("acidente vascular cerebral") || desc.contains("derrame")) {
            procedures.add(CareofPacients.CINTILOGRAFIA);
            procedures.add(CareofPacients.EXAMES_IMAGEM);
            procedures.add(CareofPacients.CIRURGIA);
            procedures.add(CareofPacients.MEDICACAO);
            procedures.add(CareofPacients.OBSERVACAO);
        } else if (proceduresToAdd != null && !proceduresToAdd.isEmpty()) {
            procedures.addAll(proceduresToAdd);
        }
    }

    public boolean canBeDiscarged(People people, FirstCare firstCare) {
        // String desc = people.getDescription().toLowerCase();
        // boolean isGrave = desc.contains("dor no peito")
        // || desc.contains("infarto")
        // || desc.contains("acidente vascular cerebral")
        // || desc.contains("derrame")
        // || desc.contains("trauma perfurante");
        // if (isGrave) {
        // return false;
        // }

        // boolean isLeve = desc.contains("febre")
        // || desc.contains("corte")
        // || desc.contains("fratura")
        // || desc.contains("dificuldade respiratória")
        // || desc.contains("falta de ar");

        // if (isLeve) {
        // boolean hasMedicacao =
        // firstCare.getProcedures().contains(CareofPacients.MEDICACAO)
        // || firstCare.getProcedures().contains(CareofPacients.CURATIVO)
        // || firstCare.getProcedures().contains(CareofPacients.SUTURA)
        // || firstCare.getProcedures().contains(CareofPacients.IMOBILIZACAO)
        // || firstCare.getProcedures().contains(CareofPacients.OXIGENIOTERAPIA);
        // return hasMedicacao;
        // }

        // return CareStatus.ALTA.equals(firstCare.getCareStatus());

        boolean isGrave = people.getSeverity() == SeverityLevel.GRAVE;
        if (isGrave) {
            return false;
        }

        return !firstCare.getProcedures().isEmpty();
    }

}
