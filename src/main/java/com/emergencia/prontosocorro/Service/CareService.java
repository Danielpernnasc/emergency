package com.emergencia.prontosocorro.Service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;

@Service
public class CareService {
    
    private final RegretsMedicService regretsMedicService;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;
   

    public CareService(
        RegretsMedicService regretsMedicService, 
        RepositoryFirstCare repositoryFirstCare, 
        RepositoryPeople repositoryPeople, 
        RepositoryHospital repositoryHospital) 
        {
        this.regretsMedicService = regretsMedicService;
        this.repositoryHospital = repositoryHospital;
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
    }

    public FirstCare createFirstCare(People people, Hospital hospital) {
        // Lógica para criar um atendimento inicial
        if(people == null || hospital == null) {
            throw new IllegalArgumentException("People and Hospital must not be null");
        }

        if(people.getStatePatient() == null){
            throw new IllegalArgumentException("StatePatient must not be null");
        }

        if(!people.getStatePatient().canReceiveCare()) {
            throw new IllegalStateException("Patient is not in a state to receive care");
        }

        if (people.getStatePatient().getStatusType() == StatusType.MORTO) {
            throw new IllegalStateException(
                "Dead people do not receive hospital care"
            );
        }

       // 🔹 garante que o paciente existe no banco
        
       People savedPeople = repositoryPeople.save(people);

        Object medicResult = regretsMedicService.defineSepSpecialistMedic(savedPeople);
        if (!(medicResult instanceof SpecialistMedic)) {
            throw new IllegalStateException("Expected SpecialistMedic from defineSepSpecialistMedic");
        }
        SpecialistMedic specialistMedic = (SpecialistMedic) medicResult;
        return new FirstCare(savedPeople, hospital, savedPeople.getStatePatient(), specialistMedic);
    }

    public void dischargePatient(FirstCare firstCare) {
        if(firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        firstCare.disCharge();
    }

    public void applyProcedures(FirstCare firstCare, CareofPacients procedure) {
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        List<CareofPacients> procedures = firstCare.getProcedures();
        String desc = firstCare.getPeople().getDescription().toLowerCase();


      if (desc.contains("febre")) {
            procedures.add(CareofPacients.MEDICACAO);

        } else if (desc.contains("corte")) {
            procedures.add(CareofPacients.CURATIVO);
            procedures.add(CareofPacients.SUTURA);

        } else if (desc.contains("fratura")) {
            procedures.add(CareofPacients.IMOBILIZACAO);
        } else   if (desc.contains("trauma perfurante")
            || desc.contains("infarto")
            || desc.contains("derrame")
            || desc.contains("acidente vascular cerebral")) {
            procedures.add(CareofPacients.CINTILOGRAFIA);
            procedures.add(CareofPacients.EXAMES_IMAGEM);
            procedures.add(CareofPacients.CIRURGIA);
            procedures.add(CareofPacients.MEDICACAO);
            procedures.add(CareofPacients.OBSERVACAO);
            } else
            if (desc.contains("dificuldade respiratória") || desc.contains("falta de ar")) {
            procedures.add(CareofPacients.OXIGENIOTERAPIA);
        } else if(desc.contains("dor no peito") || desc.contains("infarto") || desc.contains("Trauma perfurante") || desc.contains("acidente vascular cerebral") || desc.contains("derrame")){
            procedures.add(CareofPacients.CINTILOGRAFIA);
            procedures.add(CareofPacients.EXAMES_IMAGEM);
            procedures.add(CareofPacients.CIRURGIA);
            procedures.add(CareofPacients.MEDICACAO);
            procedures.add(CareofPacients.OBSERVACAO);
        } else {
            procedures.add(procedure);
        }
    }

    public boolean canBeDiscarged(People people, FirstCare firstCare) {
        String desc = people.getDescription().toLowerCase();
        if (desc.contains("dor no peito") || desc.contains("infarto") || desc.contains("acidente vascular cerebral") || desc.contains("derrame") || desc.contains("trauma perfurante"))  {
            return false;
        }
        return true;
    }

}
