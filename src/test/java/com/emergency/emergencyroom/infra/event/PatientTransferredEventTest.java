package com.emergency.emergencyroom.infra.event;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.infra.event.PatientTransferredEvent;

public class PatientTransferredEventTest {

    @Test
    void shouldPatientTransferEnvent(){
        PatientTransferredEvent eventJPA = new PatientTransferredEvent();
        eventJPA.getPatientId();
        eventJPA.getFromHospitalId();
        eventJPA.getToHospitalId();
    

        PatientTransferredEvent eventTransfer = new PatientTransferredEvent(
            "Teste",
            1L,
            1L,
            4L
        );

        assertEquals(1L, eventTransfer.getPatientId());
        assertEquals(1L, eventTransfer.getFromHospitalId());
        assertEquals(4L, eventTransfer.getToHospitalId());


        
    }

}
