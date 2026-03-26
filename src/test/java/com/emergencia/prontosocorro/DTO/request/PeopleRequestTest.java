package com.emergencia.prontosocorro.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.DTO.request.PeopleRequest;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;

public class PeopleRequestTest {

    @Test
    void ShouldPeopleRequest(){
        PeopleRequest req = new PeopleRequest(
            "Arthur de Camelot", 
            1800,
            "ATAQUE DE INFARTO",
            1L,
            SeverityLevel.GRAVE,
            List.of(ComorbidityType.ALERGIA)

        );

        assertEquals("Arthur de Camelot", req.getName());
        assertEquals(1800, req.getIdade());
        assertEquals("ATAQUE DE INFARTO", req.getDescription());
        assertEquals(1L, req.getHospitalId());
        assertEquals(SeverityLevel.GRAVE, req.getSeverityLevel());
        assertEquals(List.of(ComorbidityType.ALERGIA), req.comorbidities());
    }
}
