package com.emergencia.prontosocorro.DTO.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.DTO.response.FirstCareResponse;
import com.emergencia.prontosocorro.domain.enums.CareSector;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;

public class FirstCareResponseTest {

    @Test
    void shouldFirstCareResponse(){
        LocalDateTime now = LocalDateTime.now();
        FirstCareResponse firstCareResponse = new FirstCareResponse(
            1L,
            CareStatus.EM_ATENDIMENTO,
            SpecialistMedic.CLINICAL_MEDICINE,
            now,
            List.of(ComorbidityType.HIPERTENSAO),
            1L,
            "Alexandre Grande",
            1L,
            "Hospital Central",
            "A00",
            CareSector.CONSULTORIO
        );

        assertEquals(1L, firstCareResponse.id());
        assertEquals(CareStatus.EM_ATENDIMENTO, firstCareResponse.careStatus());
        assertEquals(SpecialistMedic.CLINICAL_MEDICINE, firstCareResponse.specialistMedic());
        assertEquals(now, firstCareResponse.careDateTime());
        assertEquals(List.of(ComorbidityType.HIPERTENSAO), firstCareResponse.comorbidities());
        assertEquals(1L, firstCareResponse.peopleId());
        assertEquals("Alexandre Grande", firstCareResponse.peopleName());
        assertEquals(1L, firstCareResponse.hospitalId());
        assertEquals("Hospital Central", firstCareResponse.hospitalName());
        assertEquals(List.of(ComorbidityType.HIPERTENSAO), firstCareResponse.comorbidities());
        
    }


   
    

}
