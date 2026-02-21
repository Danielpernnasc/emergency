package com.emergencia.prontosocorro.Domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class PeopleTest {


        @Test
        void shouldChangeStatusToCriticalWhenGrave() {

            People people = new People();

            people.changeStatus(SeverityLevel.GRAVE);

            assertEquals(StatusType.CRITICO, people.getStatusPatient());
        }

        @Test
        void shouldMarkPatientAsDead() {

            People people = new People();

            people.registerDeath("test", LocalDateTime.now());

            assertEquals(StatusType.MORTO, people.getStatusPatient());
        }

}
