package com.emergencia.prontosocorro.Domain.StateTest.StatusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.status.Interned;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public class InternedTest {

    @Test

    void internedPatientShouldReceiveCare(){
        
        Interned interned = new Interned();

        boolean canReceiveCare = interned.canReceiveCare();

        assertTrue(canReceiveCare);

    }

    @Test
    void internedPatientToStringShouldReturnInterned() {

        Interned interned = new Interned();

        String result = interned.toString();

        assertEquals("Internado", result);
    }

    @Test
    void internedPatientGetStatusTypeShouldReturnInternado() {
        Interned interned = new Interned();

        var statusType = interned.getStatusType();

        assertEquals(StatusType.INTERNADO, statusType);
    }


}
