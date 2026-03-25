package com.emergencia.prontosocorro.Domain.StateTest.StatusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.status.Dead;

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

        assertEquals(com.emergencia.prontosocorro.domain.enums.StatusType.MORTO, statusType);
    }   
        

}
