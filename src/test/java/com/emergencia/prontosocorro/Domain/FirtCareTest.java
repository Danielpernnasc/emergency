package com.emergencia.prontosocorro.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class FirtCareTest {

    @Test
    public void shouldDischargePatient() {
       People patient = new People();  
       patient.setStatusPatient(StatusType.ENFERMO);

       FirstCare firstCare = new FirstCare();
       firstCare.setPeople(patient);
       firstCare.disCharge();

       assertEquals(CareStatus.ALTA, firstCare.getCareStatus());
    }


    @Test
    public void shouldNotDischargePatient() {
        People patient = new People();
        patient.setStatusPatient(StatusType.MORTO);
        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(patient);
        
        assertThrows(IllegalStateException.class, () -> firstCare.disCharge());
    }

}