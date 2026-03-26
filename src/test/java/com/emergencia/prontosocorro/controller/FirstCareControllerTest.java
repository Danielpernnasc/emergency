package com.emergencia.prontosocorro.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.DTO.request.DeathRequest;
import com.emergencia.prontosocorro.DTO.request.FirstCareRequest;
import com.emergencia.prontosocorro.DTO.request.StateEvolutionRequest;
import com.emergencia.prontosocorro.DTO.response.FirstCareResponse;
import com.emergencia.prontosocorro.controller.FirstCareController;
import com.emergencia.prontosocorro.domain.entity.FirstCare;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.CareSector;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.CareofPacients;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.repository.RepositoryHospital;
import com.emergencia.prontosocorro.repository.RepositoryPeople;
import com.emergencia.prontosocorro.service.CareService;




@ExtendWith(MockitoExtension.class)
public class FirstCareControllerTest {

    @Mock
    RepositoryFirstCare repositoryFirstCare;

    @Mock
    RepositoryPeople repositoryPeople;

    @Mock
    RepositoryHospital repositoryHospital;

    @Mock
    CareService firstCareService;

    @InjectMocks
    FirstCareController firstCareController;


    @Test
    void shouldCreate(){
            FirstCareRequest request = new FirstCareRequest(
                1L,
                1L,
                SpecialistMedic.CLINICAL_MEDICINE,
                CareStatus.EM_ATENDIMENTO,
                "A00",
                CareSector.TRIAGEM
        );

        People people = new People();
        people.setId(1L);

        Hospital hospital = new Hospital();
        hospital.setId(1L);

        FirstCare firstCare = new FirstCare();

       firstCare.setPeople(people);
       firstCare.setHospital(hospital);

       when(repositoryPeople.findById(1L))
            .thenReturn(Optional.of(people));

        when(repositoryHospital.findById(1L))
                .thenReturn(Optional.of(hospital));

        when(firstCareService.createFirstCare(people, hospital, request))
                .thenReturn(firstCare);

        FirstCare result = firstCareController.create(request);

        assertEquals(people, result.getPeople());
        assertEquals(hospital, result.getHospital());
    }

    @Test
    void shouldFindAll(){


        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setComorbidities(List.of(ComorbidityType.OUTRA));

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("HOSPITAL CENTRAL");

 
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        firstCare.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);

        when(repositoryFirstCare.findAll()).thenReturn(List.of(firstCare));

        List<FirstCareResponse> result = firstCareController.findAll();
        assertEquals(1, result.size());
        assertEquals(CareStatus.EM_ATENDIMENTO, result.get(0).careStatus());
        assertEquals(SpecialistMedic.CLINICAL_MEDICINE, result.get(0).specialistMedic());
        assertEquals(null, result.get(0).careDateTime());
        assertEquals(people.getComorbidities(), result.get(0).comorbidities());
        assertEquals(1L, result.get(0).peopleId());
        assertEquals("Arthur de Camelot", result.get(0).peopleName());
        assertEquals(1L, result.get(0).hospitalId());
        assertEquals("HOSPITAL CENTRAL", result.get(0).hospitalName());
        assertEquals(null, result.get(0).cidCode());

    }


     @Test
    void shouldfinById(){

        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setComorbidities(List.of(ComorbidityType.OUTRA));

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("HOSPITAL CENTRAL");

 
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        firstCare.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);

     when(repositoryFirstCare.findById(1L))
        .thenReturn(Optional.of(firstCare));

       FirstCareResponse result = firstCareController.findById(1L);

        assertEquals(CareStatus.EM_ATENDIMENTO, result.careStatus());
        assertEquals(SpecialistMedic.CLINICAL_MEDICINE, result.specialistMedic());
        assertEquals(null, result.careDateTime());
        assertEquals(people.getComorbidities(), result.comorbidities());
        assertEquals(1L, result.peopleId());
        assertEquals("Arthur de Camelot", result.peopleName());
        assertEquals(1L, result.hospitalId());
        assertEquals("HOSPITAL CENTRAL", result.hospitalName());
        assertEquals(null, result.cidCode());

    }

    @Test
    void shouldaddComorbity(){

        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setComorbidities(List.of(ComorbidityType.OUTRA));

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("HOSPITAL CENTRAL");

 
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        firstCare.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);
        
        List<ComorbidityType> comorbidities =
                List.of(ComorbidityType.OUTRA);

         when(repositoryFirstCare.findById(1L))
            .thenReturn(Optional.of(firstCare));
            
        doNothing().when(firstCareService)
                .addComorbidity(firstCare, comorbidities);

        when(repositoryFirstCare.save(any(FirstCare.class)))
                .thenReturn(firstCare);

          FirstCareResponse result =
            firstCareController.addComorbidity(1L, comorbidities);
            
            assertEquals(1L, result.id());
           
    }

@Test
void shouldUpdateEvolution(){
  
        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setComorbidities(List.of(ComorbidityType.OUTRA));

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("HOSPITAL CENTRAL");

 
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        firstCare.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);


         StateEvolutionRequest request =
            new StateEvolutionRequest(
                    Set.of(CareofPacients.MEDICACAO),
                    CareStatus.EM_ATENDIMENTO
            );
        when(repositoryFirstCare.findById(1L))
            .thenReturn(Optional.of(firstCare));

        doNothing().when(firstCareService)
                    .applyProcedures(
                            eq(1L),
                            eq(firstCare),////////
                            anySet(),
                            any()
                    );
    firstCareController.updateEvolution(1L, request);
    verify(firstCareService).applyProcedures(
                eq(1L),
                eq(firstCare),
                anySet(),
                any()
        );

    }


    @Test
    void shouldRegiterDeath(){
         People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setComorbidities(List.of(ComorbidityType.OUTRA));

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("HOSPITAL CENTRAL");

 
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        firstCare.setCareStatus(CareStatus.EM_ATENDIMENTO);
        firstCare.setSpecialistMedic(SpecialistMedic.CLINICAL_MEDICINE);
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);

        LocalDateTime dateTimeDeath =  LocalDateTime.now();
        DeathRequest deathRequest = new DeathRequest(
            "Falencia multiplas do orgãos",
           dateTimeDeath
        );

         when(repositoryFirstCare.findById(1L))
            .thenReturn(Optional.of(firstCare));

    doNothing().when(firstCareService)
        .registerDeath(
                firstCare,
                deathRequest.deathCause(),
                deathRequest.deathTime()
        );

          FirstCareResponse result =
            firstCareController.registerDeath(1L, deathRequest);

    assertEquals("Arthur de Camelot", result.peopleName());
    assertEquals("HOSPITAL CENTRAL", result.hospitalName());

    verify(firstCareService)
            .registerDeath(
                    firstCare,
                    deathRequest.deathCause(),
                    deathRequest.deathTime()
            );

    }




}
