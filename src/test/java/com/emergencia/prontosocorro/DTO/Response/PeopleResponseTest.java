package com.emergencia.prontosocorro.DTO.Response;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.Domain.enums.StatusType;

public class PeopleResponseTest {

    @Test
    void shouldPeopleResponse(){
        PeopleResponse peopleResponse = new PeopleResponse(
            1L,
            "Helena",
            10000,
            "Enxaqueca",
            "ENFERMO",
            1L,
            "Hospital Central",
            List.of(ComorbidityType.OUTRA)
            
        );

        assertEquals(1L, peopleResponse.id());
        assertEquals("Helena", peopleResponse.name());
        assertEquals(10000, peopleResponse.idade());
        assertEquals("Enxaqueca", peopleResponse.description());
        assertEquals( "ENFERMO", peopleResponse.statePatient());
        assertEquals(1L, peopleResponse.hospitalId());
        assertEquals(List.of(ComorbidityType.OUTRA), peopleResponse.comorbidities());
    }

    @Test
    void shouldPeopleResponse_(){
            PeopleResponse response = new PeopleResponse(
            1L,
            "Arthur",
            40,
            "Paciente com febre",
            StatusType.URGENTE,
            10L,
            "Hospital Central",
            List.of(ComorbidityType.ALERGIA)
    );

        assertEquals(1L, response.id());
        assertEquals("Arthur", response.name());
        assertEquals(40, response.idade());
        assertEquals("Paciente com febre", response.description());
        assertEquals("URGENTE", response.statePatient()); // enum convertido para string
        assertEquals(10L, response.hospitalId());
        assertEquals("Hospital Central", response.hospitalName());
        assertTrue(response.comorbidities().contains(ComorbidityType.ALERGIA));
    }

}
