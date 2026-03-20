package com.emergencia.prontosocorro.Message.event;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PatientTransferredEventTest {

    @Test
    void shouldPatientTransferEnvent(){
        PatientTransferredEvent eventJPA = new PatientTransferredEvent();
        eventJPA.getPatientId();
        eventJPA.getFromHospitalId();
        eventJPA.getToHospitalId();
    

        PatientTransferredEvent eventTransfer = new PatientTransferredEvent(
            1L,
            1L,
            4L
        );

        assertEquals(1L, eventTransfer.getPatientId());
        assertEquals(1L, eventTransfer.getFromHospitalId());
        assertEquals(4L, eventTransfer.getToHospitalId());


        
    }

}
