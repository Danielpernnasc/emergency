package com.emergencia.prontosocorro.DTO.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
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
            CareSector.CONSULTORIO
        );


     
        assertEquals(1L, request.peopleId());
        assertEquals(2L,  request.hospitalId());
        assertEquals(SpecialistMedic.CARDIOLOGIST, request.specialistMedic());
        assertEquals(CareStatus.EM_ATENDIMENTO, request.careStatus());
        assertEquals("A00", request.cidCode());
        
        
    }

    @Test 
    void  ShouldConstructor(){
         FirstCareRequest request = new FirstCareRequest(
            null, null, null, null, null, null
         );

         request.getPeopleId();
         request.hospitalId();
         request.getSpecialistMedic();
         request.getCareStatus();
         request.getCidCode();

         assertEquals(null, request.getPeopleId());
         assertEquals(null, request.getHospitalId());
         assertEquals(null, request.getSpecialistMedic());
         assertEquals(null, request.careStatus());
         assertEquals(null, request.getCidCode());
         
    }

    @Test
    void shouldtoPeopleRequest(){

        FirstCareRequest fCareReq = new FirstCareRequest();
        fCareReq.toPeopleRequest();

        

    }

}
