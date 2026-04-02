package com.emergency.emergencyRoom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.status.Sick;

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
