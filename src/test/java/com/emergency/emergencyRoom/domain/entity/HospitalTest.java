package com.emergency.emergencyRoom.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HospitalTest {

    @Test
    void shouldCreateHospitalUsingConstructor() {

        Hospital hospital = new Hospital(
            1L,
            "Hospital Central ",
            "Avenida Central",
            100
        );

        assertEquals(1L, hospital.getId());
        assertEquals("Hospital Central ", hospital.getNameHospital());
        assertEquals("Avenida Central", hospital.getAddress());
        assertEquals(100, hospital.getNumero());
    }

    @Test
    void shouldSetAndGetHospitalFields() {
        Hospital hospital = new Hospital();

        hospital.setId(2L);
        hospital.setNameHospital("Hospital Regional");
        hospital.setAddress("Rua Regional");
        hospital.setNumero(200);

        assertEquals(2L, hospital.getId());
        assertEquals("Hospital Regional", hospital.getNameHospital());
        assertEquals("Rua Regional", hospital.getAddress());
        assertEquals(200, hospital.getNumero());
    }
}
