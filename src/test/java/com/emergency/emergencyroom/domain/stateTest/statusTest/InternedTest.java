package com.emergency.emergencyroom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.status.Interned;

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
