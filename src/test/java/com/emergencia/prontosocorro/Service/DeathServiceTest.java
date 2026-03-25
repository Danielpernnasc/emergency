package com.emergencia.prontosocorro.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.StatusType;
import com.emergencia.prontosocorro.infra.observability.ObservabilityService;


@ExtendWith(MockitoExtension.class)
public class DeathServiceTest {
    @Mock
      RepositoryPeople repositoryPeople;
    @Mock
    ObservabilityService observabilityService;

      @InjectMocks
        DeathService deathService;

    @Test
    void shouldRegisterDeath(){

        People people = new People();
        people.setStatusPatient(StatusType.MORTO);
        String justification = "Patient died due to complications from surgery.";
        LocalDateTime deathDate = LocalDateTime.now();
        try {
            deathService.registerDeath(people, justification, deathDate);
        } catch (IllegalStateException e) {
            assert(e.getMessage().equals("Patient already dead"));
        }

    }

    @Test
    void shouldUseCurrentTimeWhenDateIsNull() {

        People people = new People();
         people.setStatusPatient(StatusType.ENFERMO);

        deathService.registerDeath(people, "Heart attack", null);


        assertEquals(StatusType.MORTO, people.getStatusPatient());
        verify(observabilityService).incrementDeathRegister();
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
