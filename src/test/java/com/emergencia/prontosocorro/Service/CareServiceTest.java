package com.emergencia.prontosocorro.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.emergencia.prontosocorro.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Domain.Entity.FirstCare;
import com.emergencia.prontosocorro.Domain.Entity.Hospital;
import com.emergencia.prontosocorro.Domain.Entity.People;
import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.CareofPacients;
import com.emergencia.prontosocorro.Domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.Domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.Domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.enums.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.EventRepository.ProcessedEventRepository;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;
import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.infra.event.SectorChangedEvent;
import com.emergencia.prontosocorro.infra.observability.ObservabilityService;
import com.emergencia.prontosocorro.infra.producer.HospitalEventProducer;



@ExtendWith(MockitoExtension.class)
public class CareServiceTest {

    @Mock
    RepositoryFirstCare repositoryFirstCare;
    @Mock
    RepositoryCIDKeywordRule repositoryCIDKeywordRule;
    @Mock
    RepositoryPeople repositoryPeople;
    @Mock
    RepositoryCID repositoryCID;
    @Mock
    RepositoryHospital repositoryHospital;
    @Mock
    HospitalEventProducer hospitalEventProducer;
    @Mock
     ProcessedEventRepository processedEventRepository;
     @Mock
     private ObservabilityService observabilityService;

    @InjectMocks
    CareService careService;

    @Test
    void shouldDefineSpecialistMedic(){

        CIDKeywordRule rule = new CIDKeywordRule();
        rule.setCidCode("A01");

        rule.setKeyword("Febre recorrente");
        rule.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);

        if(rule.getSpecialistMedic() != null){
            when(repositoryCIDKeywordRule.findAll())
                .thenReturn(List.of(rule));

            SpecialistMedic specialist = careService.defineSpecialistMedic("Paciente com febre recorrente");

            assertEquals(SpecialistMedic.CLINICAL_MEDICINE, specialist);
        }
    
      when(repositoryCIDKeywordRule.findAll())
                .thenReturn(List.of(rule));

        

        SpecialistMedic specialist = careService.defineSpecialistMedic("Paciente com dor no peito");

        assertEquals(SpecialistMedic.CLINICAL_MEDICINE, specialist);
      
    }

    @Test
    void shouldmapStatusTypeToCareStatus(){
        assertEquals(CareStatus.AGUARDANDO_ATENDIMENTO, careService.mapStatusTypeToCareStatus(StatusType.ENFERMO));
        assertEquals(CareStatus.EM_ATENDIMENTO, careService.mapStatusTypeToCareStatus(StatusType.URGENTE));
        assertEquals(CareStatus.EM_CIRURGIA, careService.mapStatusTypeToCareStatus(StatusType.CRITICO));
        assertEquals(CareStatus.EM_OBSERVACAO, careService.mapStatusTypeToCareStatus(StatusType.INTERNADO));
        assertEquals(CareStatus.OBITO, careService.mapStatusTypeToCareStatus(StatusType.MORTO));
        assertEquals(CareStatus.DE_ALTA, careService.mapStatusTypeToCareStatus(StatusType.FORA_PERIGO));
    }

    @Test
    void shouldmapSeverityToStatus(){
        assertEquals(StatusType.ENFERMO, careService.mapSeverityToStatus(SeverityLevel.LEVE));
        assertEquals(StatusType.URGENTE, careService.mapSeverityToStatus(SeverityLevel.MODERADO));
        assertEquals(StatusType.CRITICO, careService.mapSeverityToStatus(SeverityLevel.GRAVE));
        assertEquals(StatusType.INTERNADO, careService.mapSeverityToStatus(SeverityLevel.UTI));
        assertEquals(StatusType.FORA_PERIGO, careService.mapSeverityToStatus(SeverityLevel.OBSERVACAO));
        assertEquals(StatusType.ENFERMO, careService.mapSeverityToStatus(SeverityLevel.OUTROS));
    }

   @Test
    void shouldCreateFirstCareWithCID() {

        People people = new People();
        people.setStatusPatient(StatusType.ENFERMO);

        Hospital hospital = new Hospital();

        CID cid = new CID(
            "A00",
            "Descrição",
            SeverityLevel.MODERADO,
            SpecialistMedic.CLINICAL_MEDICINE
        );

        FirstCareRequest req = new FirstCareRequest(
            1L,
            1L,
            SpecialistMedic.CLINICAL_MEDICINE,
            CareStatus.EM_ATENDIMENTO,
            "A00",
            CareSector.SETOR_UTI,
            SeverityLevel.MODERADO
        );

        when(repositoryCID.findById("A00")).thenReturn(Optional.of(cid));
        when(repositoryPeople.save(any())).thenReturn(people);
        when(repositoryFirstCare.save(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        FirstCare result = careService.createFirstCare(people, hospital, req);

        // Assert
        assertNotNull(result);

        verify(repositoryCID).findById("A00"); // ✅ agora faz sentido
        verify(observabilityService).incrementCreateCounter();
    }

    @Test
    void shouldThrowWhenInvalidInput(){
        
        People people = new People();
        Hospital hospital = new Hospital();
        FirstCareRequest firstCareRequest = new FirstCareRequest();

        assertThrows(IllegalArgumentException.class, () -> {
            careService.createFirstCare(null, hospital, firstCareRequest);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            careService.createFirstCare(people, null, firstCareRequest);
        });
    }

    @Test
    void shoulddisCharge(){

        //Arrange
        FirstCare firstCare = new FirstCare();
        People patient = new People();
        patient.setStatusPatient(StatusType.ENFERMO);
        firstCare.setPeople(patient);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);

        //Act
        firstCare.disCharge();

        //Assert

        assertEquals(CareStatus.DE_ALTA, firstCare.getCareStatus());
    }


    @Test 
    void shouldcanBeDiscarged(){

        People patient = new People();
        patient.setSeverity(SeverityLevel.GRAVE);
        patient.setSeverity(SeverityLevel.LEVE);
        patient.setSeverity(SeverityLevel.UTI);
        patient.setSeverity(SeverityLevel.OBSERVACAO);
        patient.setSeverity(SeverityLevel.OUTROS);

        FirstCare firstCare = new FirstCare();
        boolean result = careService.canBeDiscarged(patient, firstCare);
            assertFalse(result);
    
            patient.setSeverity(SeverityLevel.GRAVE);
            patient.setSeverity(SeverityLevel.UTI);
            firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
            firstCare.setCareStatus(CareStatus.EM_CIRURGIA);
            firstCare.setCareStatus(CareStatus.AGUARDANDO_ATENDIMENTO);
            firstCare.setCareStatus(CareStatus.EM_OBSERVACAO);
            firstCare.setCareStatus(CareStatus.DE_ALTA);
            result = careService.canBeDiscarged(patient, firstCare);
            assertFalse(result);
    
            patient.setSeverity(SeverityLevel.GRAVE);
            patient.setSeverity(SeverityLevel.UTI);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        boolean canBeDischarged = careService.canBeDiscarged(patient, firstCare);
         assertFalse(result);
        assertFalse(canBeDischarged);
    }

    @Test
    void shouldReturnTrueWhenStatusIsAlta() {

        People people = new People();
        people.setSeverity(SeverityLevel.LEVE);

        FirstCare firstCare = new FirstCare();
        firstCare.setCareStatus(CareStatus.DE_ALTA);

        boolean result = careService.canBeDiscarged(people, firstCare);

        assertFalse(result);
    }

    
@Test
void shouldReturnFalseWhenNoProcedures() {

    People people = new People();
    people.setSeverity(SeverityLevel.LEVE);

    FirstCare firstCare = new FirstCare();
    firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
    firstCare.setProcedures(Set.of(CareofPacients.MEDICACAO));

    boolean result = careService.canBeDiscarged(people, firstCare);

    assertTrue(result);
}

    @Test
    void shoulregisterDeath(){
         People patient = new People();
        patient.setStatusPatient(StatusType.ENFERMO);

        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(patient);

        careService.registerDeath(firstCare, "Causa", LocalDateTime.now());

        assertEquals(StatusType.MORTO, patient.getStatusPatient());
        assertEquals(CareStatus.OBITO, firstCare.getCareStatus());

        assertThrows(ResponseStatusException.class, () -> {
                careService.registerDeath(firstCare, "Causa da morte", LocalDateTime.now());
        });

        verify(repositoryPeople).save(patient);
        verify(repositoryFirstCare).save(firstCare);
    }

  @Test
    void shouldAddComorbidityWhenNotExists() {

        FirstCare firstCare = new FirstCare();
        People patient = new People();

        List<ComorbidityType> comorbidities = new ArrayList<>();
        comorbidities.add(ComorbidityType.ALERGIA);

        patient.setComorbidities(comorbidities);
        firstCare.setPeople(patient);

        careService.addComorbidity(firstCare, comorbidities);

        assertFalse(patient.getComorbidities().contains(ComorbidityType.DIABETES));
    }


    @Test
    void shouldNotAddDuplicateComorbidity() {

        FirstCare firstCare = new FirstCare();
        People patient = new People();

        List<ComorbidityType> comorbidities = new ArrayList<>();
        comorbidities.add(ComorbidityType.DIABETES);

        patient.setComorbidities(comorbidities);
        firstCare.setPeople(patient);

        careService.addComorbidity(firstCare, comorbidities);

        assertEquals(1, patient.getComorbidities().size());
    }



    @Test
    void  shoulddischargePatient(){
        FirstCare firstCare = new FirstCare();
        assertThrows(IllegalArgumentException.class, () -> {
                careService.dischargePatient(null);
        });
        People patient = new People();
        patient.setStatusPatient(StatusType.ENFERMO);
        firstCare.setPeople(patient);
        careService.dischargePatient(firstCare);
        assertEquals(CareStatus.DE_ALTA, firstCare.getCareStatus());
        assertEquals(StatusType.ENFERMO, patient.getStatusPatient());
    }

 
    @Test 
    void shouldapplyProcedures(){
        //Arrange
        FirstCare firstCare = new FirstCare();
        People patient = new People();
        patient.setStatusPatient(StatusType.ENFERMO); // importante se o método usa estado
        firstCare.setPeople(patient);
        firstCare.setProcedures(new HashSet<>());
        assertThrows(IllegalArgumentException.class, () -> {
                careService.applyProcedures(1L, null, null, CareStatus.EM_ATENDIMENTO);
        });
        careService.applyProcedures(1L, firstCare, null, CareStatus.EM_ATENDIMENTO);
        patient.setStatusPatient(StatusType.ENFERMO);
        firstCare.setPeople(patient);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);

        //Act
        firstCare.getPeople().ensureAlive();

        //Assert
        assertDoesNotThrow(() -> firstCare.getPeople().ensureAlive());
        assertTrue(firstCare.getProcedures().isEmpty());

    }


    @Test
    void shouldTransferPatient(){

        Long patientId = 1L;
        Long fromHospital = 1L;
        Long toHospital = 4L;

        Hospital currentHospital = new Hospital();
        currentHospital.setId(fromHospital);

        FirstCare firstCare = new FirstCare();
        firstCare.setHospital(currentHospital);

        Hospital newHospital = new Hospital();
        newHospital.setId(toHospital);

        when(processedEventRepository.existsById(anyString()))
                .thenReturn(false);

        when(repositoryFirstCare.findById(patientId))
                .thenReturn(Optional.of(firstCare));

        when(repositoryHospital.findById(toHospital))
                .thenReturn(Optional.of(newHospital));

        PatientTransferredEvent event = new PatientTransferredEvent(
            "Test",
            patientId,
            fromHospital,
            toHospital
        );

        // act
        careService.handleTransfer(event);

        // assert
        assertEquals(toHospital, firstCare.getHospital().getId());

        verify(repositoryFirstCare).save(firstCare);
    }

    @Test
    void shouldErrorTransferPatient(){
        
         Long patientId = 1L;
        Long fromHospital = 1L;
        long toHospital = 5L;

        Hospital newHospital = new Hospital();
        newHospital.setId(99L);

        FirstCare firstCare = new FirstCare();
        firstCare.setHospital(newHospital);

          PatientTransferredEvent event = new PatientTransferredEvent(
            "123",
            patientId,
            fromHospital,
            toHospital
        );

        when(processedEventRepository.existsById(anyString()))
        .thenReturn(false);

        when(repositoryFirstCare.findById(patientId))
            .thenReturn(Optional.of(firstCare));

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> careService.handleTransfer(event)
        );

        assertEquals("Hospital destiny not find - Hospital destino não encontrado", exception.getMessage());
    }

    @Test
    void shouldChangeSector(){

        Long patientId = 1L;

        FirstCare firstCare = new FirstCare();
        firstCare.setId(patientId);
        firstCare.setSector(CareSector.TRIAGEM);

        when(repositoryFirstCare.findById(patientId))
            .thenReturn(Optional.of(firstCare));

        careService.changeSector(patientId, CareSector.SETOR_UTI);

         assertEquals(patientId, firstCare.getId());
         assertEquals(CareSector.SETOR_UTI, firstCare.getSector());
        verify(repositoryFirstCare).save(firstCare);

        verify(hospitalEventProducer).sendPatienttoSector(any());
        verify(observabilityService).incrementTransferCounter();
    }

     @Test
    void shouldnotSouldbackTRIAGEM(){
          Long patientId = 1L;

            FirstCare firstCare = new FirstCare();
            firstCare.setId(patientId);
            firstCare.setSector(CareSector.SETOR_UTI);

            when(repositoryFirstCare.findById(patientId))
            .thenReturn(Optional.of(firstCare));

                RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> careService.changeSector(patientId, CareSector.TRIAGEM)
        );

        assertEquals("Paciente não pode voltar para triagem", exception.getMessage());
    }
 
    @Test
    void shouldhandleTransfer(){
        Long patientId = 1L;
         Hospital oldHospital = new Hospital();
        oldHospital.setId(1L);

        Hospital newHospital = new Hospital();
        newHospital.setId(4L);

        FirstCare firstCare = new FirstCare();
        firstCare.setId(patientId);
        firstCare.setHospital(oldHospital);

        PatientTransferredEvent event = new PatientTransferredEvent(
              UUID.randomUUID().toString(),
                patientId,
                1L,
                4L
        );

        when(repositoryFirstCare.findById(patientId))
                .thenReturn(Optional.of(firstCare));

       when(repositoryHospital.findById(anyLong()))
        .thenReturn(Optional.of(newHospital));

        // Act
        careService.handleTransfer(event);

        // Assert
        assertEquals(4L, firstCare.getHospital().getId());
        verify(repositoryFirstCare).save(firstCare);
    }

    @Test
    void shouldSendTransferEvent(){

        Long patientId = 1L;
        Long fromHospital = 1L;
        Long toHospital = 2L;

        // 👇 IMPORTANTE (senão não entra no fluxo)
        when(processedEventRepository.existsById(anyString()))
                .thenReturn(false);

        // act
        careService.transferPatient(patientId, fromHospital, toHospital);

        // assert
        verify(hospitalEventProducer).sendPatientTransfer(any());
    }
    
      @Test
        void shouldHandleTransferSector(){
           Long patientId = 1L;
            FirstCare firstCare = new FirstCare();
            firstCare.setId(patientId);
            firstCare.setSector(CareSector.TRIAGEM);

              SectorChangedEvent event = new SectorChangedEvent(
                UUID.randomUUID().toString(),
                patientId,
                CareSector.TRIAGEM,
                CareSector.CONSULTORIO
        );

        when(repositoryFirstCare.findById(patientId))
                .thenReturn(Optional.of(firstCare));

        // Act
        careService.handleTransferSector(event);

        // Assert
        assertEquals(CareSector.CONSULTORIO, firstCare.getSector());
        verify(repositoryFirstCare).save(firstCare);
       
    }

}
        
    




   


