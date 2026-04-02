package com.emergency.emergencyroom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.DTO.request.FirstCareRequest;
import com.emergency.emergencyroom.domain.enums.CareSector;
import com.emergency.emergencyroom.domain.enums.CareStatus;
import com.emergency.emergencyroom.domain.enums.SeverityLevel;
import com.emergency.emergencyroom.domain.enums.SpecialistMedic;

class FirstCareRequestTest {
    @Test
    void shouldFirtCareRequest(){
        FirstCareRequest request = new FirstCareRequest(
            1L,
            2L,
            SpecialistMedic.CARDIOLOGIST,
            CareStatus.EM_ATENDIMENTO,
            "A00",
            CareSector.CONSULTORIO,
            SeverityLevel.LEVE

        );


     
        assertEquals(1L, request.peopleId());
        assertEquals(2L,  request.hospitalId());
        assertEquals(SpecialistMedic.CARDIOLOGIST, request.specialistMedic());
        assertEquals(CareStatus.EM_ATENDIMENTO, request.careStatus());
        assertEquals("A00", request.cidCode());
        assertEquals(CareSector.CONSULTORIO, request.sector());
        assertEquals(SeverityLevel.LEVE, request.severityLevel());
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
