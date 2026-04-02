package com.emergency.emergencyRoom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.domain.state.status.Dead;

public class DeadTest {
    @Test
    void deadPatientShouldNotReceiveCare() {
        Dead dead = new Dead();

        boolean canReceiveCare = dead.canReceiveCare();

        assertFalse(canReceiveCare);
    }

    @Test
    void deadPatientToStringShouldReturnDead() {
        Dead dead = new Dead();

        String result = dead.toString();

        assertEquals("Morto", result);
    }

    @Test

    void deadPatientGetStatusTypeShouldReturnObito() {
        Dead dead = new Dead();

        var statusType = dead.getStatusType();

        assertEquals(com.emergency.emergencyRoom.domain.enums.StatusType.MORTO, statusType);
    }   
        

}
