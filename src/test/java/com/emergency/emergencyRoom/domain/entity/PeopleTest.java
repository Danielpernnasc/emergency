package com.emergency.emergencyRoom.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.emergency.emergencyRoom.domain.enums.SeverityLevel;
import com.emergency.emergencyRoom.domain.enums.StatusType;

public class PeopleTest {

        @Test
        void shouldCreatePeopleUsingConstructor() {

            Hospital hospital = new Hospital();

            People people = new People(
                    "Helena",
                    25,
                    "Dor de cabeça",
                    hospital,
                    StatusType.ENFERMO,
                    SeverityLevel.LEVE
            );

            assertEquals("Helena", people.getName());
            assertEquals(25, people.getAge());
            assertEquals("Dor de cabeça", people.getDescription());
            assertEquals(hospital, people.getHospital());
            assertEquals(StatusType.ENFERMO, people.getStatusPatient());
            assertEquals(SeverityLevel.LEVE, people.getSeverity());
        }

        @Test
        void shouldSetAndGetPeopleFields() {

            People people = new People();

            people.setId(1L);
            people.setName("Aquiles");
            people.setAge(35);
            people.setDescription("Dor no peito");

            assertEquals(1L, people.getId());
            assertEquals("Aquiles", people.getName());
            assertEquals(35, people.getAge());
            assertEquals("Dor no peito", people.getDescription());
}

     
        @ParameterizedTest
        @CsvSource({
            "GRAVE, CRITICO",
            "MODERADO, URGENTE",
            "LEVE, ENFERMO",
            "UTI, INTERNADO"
        })

        void shouldChangeStatusBasedOnSeverity(SeverityLevel severity, StatusType expectedStatus) {

            People people = new People();
            people.changeStatus(severity);

            assertEquals(expectedStatus, people.getStatusPatient());
        }
     
        @Test
        void shouldMarkPatientAsDead() {

            People people = new People();

            LocalDateTime deathTime = LocalDateTime.now();

            people.registerDeath("test", deathTime);

            assertEquals(StatusType.MORTO, people.getStatusPatient());
            assertEquals(deathTime, people.getDeathTime());
        }

}
