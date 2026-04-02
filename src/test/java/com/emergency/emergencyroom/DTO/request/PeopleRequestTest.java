package com.emergency.emergencyroom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.DTO.request.PeopleRequest;
import com.emergency.emergencyroom.domain.enums.ComorbidityType;
import com.emergency.emergencyroom.domain.enums.SeverityLevel;

class PeopleRequestTest {

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

        assertEquals("Arthur de Camelot", req.name());
        assertEquals(1800, req.age());
        assertEquals("ATAQUE DE INFARTO", req.description());
        assertEquals(1L, req.hospitalId());
        assertEquals(SeverityLevel.GRAVE, req.severityLevel());
        assertEquals(List.of(ComorbidityType.ALERGIA), req.comorbidities());
    }
}
