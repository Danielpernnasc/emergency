package com.emergency.emergencyRoom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.DTO.request.HospitalRequest;

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
        assertEquals(100, req.numero());

    }

    @Test 
    void shouldGetHospital(){
        HospitalRequest req = new HospitalRequest();

        req.getNameHospital();
        req.getAddress();
        req.getNumero();

        assertEquals(null, req.getNameHospital());
        assertEquals(null, req.getAddress());
        assertEquals(0, req.getNumero());
    }

}
