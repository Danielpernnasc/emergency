package com.emergency.emergencyroom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergency.emergencyroom.domain.entity.People;
import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.infra.observability.ObservabilityService;
import com.emergency.emergencyroom.repository.RepositoryPeople;
import com.emergency.emergencyroom.service.DeathService;



@ExtendWith(MockitoExtension.class)
class DeathServiceTest {
    @Mock
      RepositoryPeople repositoryPeople;
    @Mock
    ObservabilityService observabilityService;

      @InjectMocks
        DeathService deathService;

    @Test
        void shouldRegisterDeath(){

            deathService.registerDeath(
                new People(),   
                "Heart attack",
                LocalDateTime.now()
            );

            verify(repositoryPeople).save(any(People.class));
        }

    @Test
    void shouldUseCurrentTimeWhenDateIsNull() {

        People people = new People();
         people.setStatusPatient(StatusType.ENFERMO);

        deathService.registerDeath(people, "Heart attack", null);


        assertEquals(StatusType.MORTO, people.getStatusPatient());
        verify(observabilityService).incrementDeathCounter();
    }

    @Test
    void shouldThrowExceptionWhenJustificationIsNull() {

        People people = new People();
        people.setStatusPatient(StatusType.ENFERMO); // importante

        assertThrows(IllegalStateException.class, () -> {
            deathService.registerDeath(people, null, LocalDateTime.now());
        });

        assertThrows(IllegalStateException.class, () -> {
            deathService.registerDeath(people, "   ", LocalDateTime.now());
        });

    }



   


}
