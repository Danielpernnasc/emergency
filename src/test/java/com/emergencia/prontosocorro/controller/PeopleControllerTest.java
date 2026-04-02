package com.emergencia.prontosocorro.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.emergencia.prontosocorro.DTO.request.PeopleRequest;
import com.emergencia.prontosocorro.DTO.request.StatePatientRequest;
import com.emergencia.prontosocorro.DTO.response.PeopleResponse;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.StatusType;
import com.emergencia.prontosocorro.repository.RepositoryPeople;
import com.emergencia.prontosocorro.service.PeopleService;


@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {

    @Mock
    RepositoryPeople repositoryPeople;

    @Mock
    private PeopleService peopleService;


    @InjectMocks
    private PeopleController peopleController;

    @Test
    void shouldcreatePatient(){

           PeopleRequest req = new PeopleRequest(
                "Arthur de Camelot",
                1500,
                "Dor no peito",
                1L,
                SeverityLevel.GRAVE,
                List.of(ComorbidityType.OUTRA)
        );

         People people = new People();
        people.setName("Arthur de Camelot");
        people.setAge(1500);
       when(peopleService.createPatient(any(), any()))
                .thenReturn(people);

        ResponseEntity<People> response = peopleController.createPatient(req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Arthur de Camelot", response.getBody().getName());
        assertEquals(1500, response.getBody().getAge());
    }

    @Test
    void shouldFindAll(){

        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("Hospital Central");

        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setAge(1500);
        people.setDescription("Infarto");
        people.setStatusPatient(StatusType.URGENTE);
        people.setHospital(hospital);
        people.setComorbidities(List.of(ComorbidityType.HIPERTENSAO));

        when(repositoryPeople.findAll()).thenReturn(List.of(people));

        List<PeopleResponse> result = peopleController.findAll();

        assertEquals(1, result.size());
        assertEquals("Arthur de Camelot", result.get(0).name());
        assertEquals("Hospital Central", result.get(0).hospitalName());
    
    }

    @Test
    void shouldfindById(){
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("Hospital Central");

        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setAge(1500);
        people.setDescription("Infarto");
        people.setStatusPatient(StatusType.URGENTE);
        people.setHospital(hospital);
        people.setComorbidities(List.of(ComorbidityType.HIPERTENSAO));

             when(peopleService.getStatePatientById(1L))
                .thenReturn(people);

        People result = peopleController.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdatePatiente(){
        People people = new People();
        people.setId(1L);
        people.setName("Arthur de Camelot");
        people.setAge(1500);
        people.setDescription("Infarto");
        people.setStatusPatient(StatusType.URGENTE);

        //  when(peopleService.updatedPatient(eq(1L), any(People.class)))
        //         .thenReturn(people);
        
         when(peopleService.updatedPatient(eq(1L), any(PeopleRequest.class)))
                .thenReturn(people);

        People result = peopleController.updatedPatient(1L, new PeopleRequest(
            "Arthur de Camelot",
            1500,
            "Infarto",
            1L,
            SeverityLevel.GRAVE,
            List.of(ComorbidityType.OUTRA)
        ));

        assertEquals(1L, result.getId());
    }
    @Test
    void shouldMistakeStatus(){

        StatePatientRequest req = new StatePatientRequest(
            StatusType.CRITICO,
            "Infarto",
            LocalDateTime.now()
        );

           when(peopleService.mistakeStatus(
            eq(1L),
            eq(StatusType.CRITICO),
            anyString()))
    .thenReturn(true);

       ResponseEntity<Long> response =
            peopleController.mistakeStatus(1L, req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());


    }
    @Test
    void houldReturnConflictWhenMistakeStatusIsFalse(){

        StatePatientRequest req = new StatePatientRequest(
            StatusType.MORTO,
            "Infarto",
            LocalDateTime.now()
        );

           when(peopleService.mistakeStatus(
            eq(1L),
            eq(StatusType.MORTO),
            anyString()))
          .thenReturn(false);

       ResponseEntity<Long> response =
            peopleController.mistakeStatus(1L, req);

        assertNull(null);

         assertEquals(HttpStatus.CONFLICT, response.getStatusCode());


        

    }

    


}
