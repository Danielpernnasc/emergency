package com.emergency.emergencyroom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.DTO.request.HospitalRequest;

public class HospitalRequestTest {

    @Test
    void shouldHospitalRequest(){
        HospitalRequest req = new HospitalRequest(
            "Hospital Central",
            "Avenida Central",
            100
        );

        assertEquals("Hospital Central",  req.nameHospital());
        assertEquals("Avenida Central", req.address());
        assertEquals(100, req.number());

    }

    @Test 
    void shouldGetHospital(){
        HospitalRequest req = new HospitalRequest();

        req.nameHospital();
        req.address();
        req.number();

        assertEquals(null, req.nameHospital());
        assertEquals(null, req.address());
        assertEquals(0, req.number());
    }

}
