package com.emergencia.prontosocorro.Domain.StateTest.StatusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.status.Sick;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public class Sicktest {

    @Test
    void sickPatientShouldReceiveCare() {
        Sick sick = new Sick();

        boolean canReceiveCare = sick.canReceiveCare();

        assertTrue(canReceiveCare);
    }

    @Test 
    void sickPatientToStringShouldReturnSick() {
        Sick sick = new Sick();

        String result = sick.toString();

        assertEquals("Enfermo", result);
    }

    @Test
    void sickPatientGetStatusTypeShouldReturnEnfermo() {
        Sick sick = new Sick();

        var statusType = sick.getStatusType();  
    
        assertEquals(StatusType.ENFERMO, statusType);
    }

}
