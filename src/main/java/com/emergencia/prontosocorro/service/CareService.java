package com.emergencia.prontosocorro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emergencia.prontosocorro.DTO.request.FirstCareRequest;
import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.domain.entity.CIDKeywordRule;
import com.emergencia.prontosocorro.domain.entity.FirstCare;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.CareSector;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.CareofPacients;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.domain.enums.StatusType;
import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.infra.event.ProcessedEvent;
import com.emergencia.prontosocorro.infra.event.SectorChangedEvent;
import com.emergencia.prontosocorro.infra.observability.ObservabilityService;
import com.emergencia.prontosocorro.infra.producer.HospitalEventProducer;
import com.emergencia.prontosocorro.repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.repository.RepositoryHospital;
import com.emergencia.prontosocorro.repository.RepositoryPeople;
import com.emergencia.prontosocorro.repository.eventRepository.ProcessedEventRepository;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

import org.slf4j.Logger;

@Service
public class CareService {

     private static final Logger log = LoggerFactory.getLogger(CareService.class);

    private final RepositoryPeople repositoryPeople;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryCID repositoryCID;
    private final RepositoryCIDKeywordRule repositoryCIDKeywordRule;
    private final HospitalEventProducer hospitalEventProducer;
    private final RepositoryHospital repositoryHospital;
    private final ProcessedEventRepository processedEventRepository;

   private final ObservabilityService observabilityService;

    public CareService(
            RepositoryFirstCare repositoryFirstCare,
            RepositoryPeople repositoryPeople,
            RepositoryCIDKeywordRule repositoryCIDKeywordRule,
            RepositoryCID repositoryCID,
            HospitalEventProducer hospitalEventProducer,
            RepositoryHospital repositoryHospital,
            ProcessedEventRepository processedEventRepository,
            ObservabilityService observabilityService
        ) {
        this.repositoryPeople = repositoryPeople;
        this.repositoryFirstCare = repositoryFirstCare;
        this.repositoryCIDKeywordRule = repositoryCIDKeywordRule;
        this.repositoryCID = repositoryCID;
        this.hospitalEventProducer = hospitalEventProducer;
        this.repositoryHospital = repositoryHospital;
        this.processedEventRepository = processedEventRepository;
        this.observabilityService = observabilityService;
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
        observabilityService.incrementCreateCounter();
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
            
            SeverityLevel severity = cid.getSeverityLevel();

            if (severity == null) {
                severity = SeverityLevel.LEVE; // fallback
            }

            people.changeStatus(severity);
        }

        repositoryPeople.save(people);

        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);
        firstCare.setCid(cid);
        firstCare.setSpecialistMedic(req.specialistMedic());
        firstCare.setCareStatus(req.careStatus());
        firstCare.setSector(req.sector());
        firstCare.setSeverity(req.severityLevel());

        return repositoryFirstCare.save(firstCare);
        }

    public void dischargePatient(FirstCare firstCare) {
        if (firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        firstCare.disCharge();
    }


    public void applyProcedures(Long id,  FirstCare firstCare, Set<CareofPacients> proceduresToAdd, CareStatus newStatus) {

         observabilityService.incrementUpdateCounter();
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
         observabilityService.incrementDeathCounter();
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
         
        observabilityService.incrementUpdateCounter();
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

    public void transferPatient(Long patientId, Long fromHospital, Long toHospital){

        observabilityService.incrementTransferCounter();
        PatientTransferredEvent event = new PatientTransferredEvent(
                UUID.randomUUID().toString(), 
                patientId,
                fromHospital,
                toHospital
            );
        if(processedEventRepository.existsById(event.getEventId())){
            log.info("Event already processed: {}",  event.getEventId());
            return;
        }

        hospitalEventProducer.sendPatientTransfer(event);
        log.info("Event sent - Evento enviado: {}", event.getEventId());
    }


	public void handleTransfer(PatientTransferredEvent event) {
        
        log.info("🔄 Processando evento {}", event.getEventId());

        if(processedEventRepository.existsById(event.getEventId())){
            log.info("Event already processed: {}", event.getEventId());
            return;
        }

        FirstCare firstCare = repositoryFirstCare.findById(event.getPatientId())
                .orElseThrow(() -> new RuntimeException("Care Assistant not find - Atendimento não encontrado"));

        Hospital newHospital = repositoryHospital.findById(event.getToHospitalId()) 
                .orElseThrow(() -> new RuntimeException("Hospital destiny not find - Hospital destino não encontrado"));
                
          if(!Objects.equals(firstCare.getHospital().getId(), event.getFromHospitalId())){
            throw new RuntimeException("Paciente não está nesse hospital");
        }

        firstCare.setHospital(newHospital);

        repositoryFirstCare.save(firstCare);
      

        processedEventRepository.save(
            ProcessedEvent.of(event.getEventId())
        );

        System.out.println("FirstCare hospital: " + firstCare.getHospital().getId());
        System.out.println("Event fromHospital: " + event.getFromHospitalId());

        log.info("Event processed successfully - Evento processado com sucesso: {}", event.getEventId());
     
      
    }

    public void changeSector(Long patientId, CareSector newSector){
       
        observabilityService.incrementTransferCounter();
        FirstCare firstCare = repositoryFirstCare.findById(patientId)
                        .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        CareSector current = firstCare.getSector();

        if(current == CareSector.SETOR_UTI && newSector == CareSector.TRIAGEM){
            throw new RuntimeException("Paciente não pode voltar para triagem");
        }

        if (current == CareSector.CENTRO_CIRURGICO && newSector == CareSector.TRIAGEM){
            throw new RuntimeException("Fluxo inválido");
        }

        firstCare.setSector(newSector);
        repositoryFirstCare.save(firstCare);
           SectorChangedEvent event = new SectorChangedEvent(
            UUID.randomUUID().toString(),
            patientId,
            current,
            newSector
    );

       hospitalEventProducer.sendPatienttoSector(event);
    }

    public void handleTransferSector(SectorChangedEvent event){

        log.info("Processing event - Processando evento {}", event.getEventString());

        if(processedEventRepository.existsById(event.getEventString())){
            log.info("Event already processed - Evento já processado: {}", event.getEventString());
            return;
        }

        FirstCare firstCare = repositoryFirstCare.findById(event.getPatientId())
                            .orElseThrow(() -> new RuntimeException("Atendimento não econtrado"));
            
            firstCare.setSector(event.getTo());
            
            repositoryFirstCare.save(firstCare);
           processedEventRepository.save(
              ProcessedEvent.of(event.getEventString())
            );

            log.info("Event processed successfully - Evento processado com sucesso: {}", event.getEventString());
    }
}
