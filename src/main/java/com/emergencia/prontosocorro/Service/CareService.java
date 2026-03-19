package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emergencia.prontosocorro.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Domain.Entity.FirstCare;
import com.emergencia.prontosocorro.Domain.Entity.Hospital;
import com.emergencia.prontosocorro.Domain.Entity.People;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.CareofPacients;
import com.emergencia.prontosocorro.Domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.Domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.Domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.enums.StatusType;
import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.Message.producer.HospitalEventProducer;
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
     private final HospitalEventProducer hospitalEventProducer;

    public CareService(
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryCIDKeywordRule repositoryCIDKeywordRule,
            RepositoryCID repositoryCID,
            HospitalEventProducer hospitalEventProducer
        ) {
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
        this.repositoryCIDKeywordRule = repositoryCIDKeywordRule;
        this.repositoryCID = repositoryCID;
        this.hospitalEventProducer = hospitalEventProducer;


    }

    public SpecialistMedic defineSpecialistMedic(String description) {
        List<CIDKeywordRule> rules = repositoryCIDKeywordRule.findAll();

        String desc = description.toLowerCase();
        
        for (CIDKeywordRule rule : rules) {

            if (desc.toLowerCase().contains(rule.getKeyword().toLowerCase()) 
                && rule.getSpecialistMedic() != null){
                return rule.getSpecialistMedic();
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
            case FORA_PERIGO -> CareStatus.DE_ALTA;
        };
    }
    
    public StatusType mapSeverityToStatus(SeverityLevel severity) {
      

        return switch (severity) {
            case GRAVE -> StatusType.CRITICO;
            case UTI -> StatusType.INTERNADO;
            case MODERADO -> StatusType.URGENTE;
            case LEVE, OUTROS -> StatusType.ENFERMO;
            case OBSERVACAO -> StatusType.FORA_PERIGO;
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

        people.ensureAlive();

        List<ComorbidityType> reqComorbidities = people.getComorbidities();

        if(reqComorbidities != null && !reqComorbidities.isEmpty()) {
            people.setComorbidities(reqComorbidities);
        }

      
    
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


    public void applyProcedures(Long id,  FirstCare firstCare, Set<CareofPacients> proceduresToAdd, CareStatus newStatus) {

     
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
        careStatus == CareStatus.EM_OBSERVACAO ||
        careStatus == CareStatus.DE_ALTA) {
          if (isCriticalCare(firstCare) || isSevereCase(people) || !hasProcedures(firstCare)) {
              return false;
          }
        }else if(careStatus == CareStatus.DE_ALTA || careStatus == CareStatus.OBITO) {
            return true;
        }

        return !firstCare.getProcedures().isEmpty();
    }

    public void registerDeath(FirstCare firstCare, String cause, LocalDateTime deathTime) {
        firstCare.getPeople().ensureAlive();
        People people = firstCare.getPeople();
        if(people.getStatusPatient() == StatusType.MORTO) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient is already registered as deceased");
        }

        people.registerDeath(cause, deathTime);

        firstCare.setCareStatus(CareStatus.OBITO);
           repositoryPeople.save(people);
        repositoryFirstCare.save(firstCare);
    }
    
    public void addComorbidity(FirstCare firstCare,  List<ComorbidityType> comorbidites) {

        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }

        People people = firstCare.getPeople();

        if (people.getComorbidities() == null) {
            people.setComorbidities(new ArrayList<>());
        }

       for (ComorbidityType comorbidity : comorbidites) {
        if (!people.getComorbidities().contains(comorbidity)) {
            people.getComorbidities().add(comorbidity);
        }
        }

        repositoryPeople.save(people);
    }

       public void transferPatient(long patientId, Long fromHospital, Long toHospital){
         PatientTransferredEvent event = new PatientTransferredEvent(patientId, fromHospital, toHospital);
         hospitalEventProducer.sendPatientTransfer(event);
    }

}
