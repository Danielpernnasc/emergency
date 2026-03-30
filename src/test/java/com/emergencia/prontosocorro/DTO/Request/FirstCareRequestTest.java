package com.emergencia.prontosocorro.DTO.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.Domain.enums.SpecialistMedic;

public class FirstCareRequestTest {
    @Test
    void shouldFirtCareRequest(){
        FirstCareRequest request = new FirstCareRequest(
            1L,
            2L,
            SpecialistMedic.CARDIOLOGIST,
            CareStatus.EM_ATENDIMENTO,
            "A00",
            CareSector.CONSULTORIO,
            SeverityLevel.GRAVE 
        );


     
        assertEquals(1L, request.peopleId());
        assertEquals(2L,  request.hospitalId());
        assertEquals(SpecialistMedic.CARDIOLOGIST, request.specialistMedic());
        assertEquals(CareStatus.EM_ATENDIMENTO, request.careStatus());
        assertEquals("A00", request.cidCode());
        assertEquals(CareSector.CONSULTORIO, request.sector());
        assertEquals(SeverityLevel.GRAVE, request.severityLevel());
        
        
    }

    @Test 
    void  ShouldConstructor(){
         FirstCareRequest request = new FirstCareRequest(
            null, null, null, null, null, null, null
         );

         request.peopleId();
         request.hospitalId();
         request.specialistMedic();
         request.careStatus();
         request.cidCode();
         request.sector();
        request.severityLevel();

         assertEquals(null, request.peopleId());
         assertEquals(null, request.hospitalId());
         assertEquals(null, request.specialistMedic());
         assertEquals(null, request.careStatus());
         assertEquals(null, request.cidCode());
         assertEquals(null, request.sector());
         assertEquals(null, request.severityLevel());
    }

    @Test
    void shouldtoPeopleRequest(){

        FirstCareRequest fCareReq = new FirstCareRequest();
        fCareReq.toPeopleRequest();

        

    }

}
