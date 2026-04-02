package com.emergencia.prontosocorro.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.DTO.request.PeopleRequest;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.StatusType;
import com.emergencia.prontosocorro.infra.observability.ObservabilityService;
import com.emergencia.prontosocorro.repository.RepositoryHospital;
import com.emergencia.prontosocorro.repository.RepositoryPeople;



@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    RepositoryPeople repositoryPeople;

    @Mock
    RepositoryHospital repositoryHospital;

    @Mock
    ObservabilityService observabilityService;

    @InjectMocks
    PeopleService peopleService;

    @Test
    void shouldGetStatePatientById() {
       People people = new People();
        people.setStatusPatient(StatusType.ENFERMO);

        when(repositoryPeople.findById(1L))
                .thenReturn(Optional.of(people));

        People state = peopleService.getStatePatientById(1L);

        assertNotNull(state);
    }

    @Test
    void shouldcreatePatient(){

        People people = new People();
        Hospital hospital = new Hospital();
        people.setStatusPatient(StatusType.ENFERMO);
        hospital.setId(1L);
        PeopleRequest reqPeople = new PeopleRequest();


        when(repositoryPeople.save(any(People.class)))
                .thenReturn(people);
                
        when(repositoryHospital.findById(reqPeople.hospitalId()))
                .thenReturn(Optional.of(hospital));

        People created = peopleService.createPatient(people, reqPeople);

        assertNotNull(created);
        assertEquals(StatusType.ENFERMO, created.getStatusPatient());

        verify(observabilityService).incrementCreateCounter();

    }

     @Test 
     void shouldUpdatedPatient() {
        People existingPatient = new People();
        existingPatient.setId(1L);
        existingPatient.setName("Old Name");
        existingPatient.setAge(30);
        existingPatient.setDescription("Old Description");
        existingPatient.setSeverity(null);
        existingPatient.setComorbidities(null);

        People updatedPatient = new People();
        updatedPatient.setName("New Name");
        updatedPatient.setAge(40);
        updatedPatient.setDescription("New Description");
        updatedPatient.setSeverity(null);
        updatedPatient.setComorbidities(null);

        when(repositoryPeople.findById(1L))
                .thenReturn(Optional.of(existingPatient));
        when(repositoryPeople.save(any(People.class)))
                .thenReturn(existingPatient);

        People result = peopleService.updatedPatient(1L, updatedPatient);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals(40, result.getAge());
        assertEquals("New Description", result.getDescription());
     }

    @Test
    void shouldregisterDeath(){
        People people = new People();
        people.setStatusPatient(StatusType.MORTO);

        when(repositoryPeople.findById(1L))
                .thenReturn(Optional.of(people));

        DeathService deathService = mock(DeathService.class);
        peopleService = new PeopleService(repositoryPeople, deathService, null, null);
        peopleService.registerDeath(1L, "test", null);

        assertEquals(StatusType.MORTO, people.getStatusPatient());
    }

    @Test 
    void shouldMistakeStatus() {
        People people = new People();
        people.setStatusPatient(StatusType.MORTO);

        when(repositoryPeople.findById(1L))
                .thenReturn(Optional.of(people));

        boolean result = peopleService.mistakeStatus(1L, StatusType.ENFERMO, "test");

        assertTrue(result);
        assertEquals(StatusType.ENFERMO, people.getStatusPatient());
    }

}
