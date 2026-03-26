package com.emergencia.prontosocorro.domain.stateTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.StatePatient;
import com.emergencia.prontosocorro.domain.state.status.Critical;
import com.emergencia.prontosocorro.domain.state.status.Dead;
import com.emergencia.prontosocorro.domain.state.status.Interned;
import com.emergencia.prontosocorro.domain.state.status.Sick;
import com.emergencia.prontosocorro.domain.state.status.Urgent;

public class StatePatientTest {

    @Test
    void sickPatientShouldReceiveCare() {
        StatePatient state = new Sick();
        assertTrue(state.canReceiveCare());
    }   

    @Test
    void urgentPatientShouldReceiveCare() {
        StatePatient state = new Urgent();
        assertTrue(state.canReceiveCare());
    }   

    @Test
    void criticalPatientShouldReceiveCare() {
        StatePatient state = new Critical();
        assertTrue(state.canReceiveCare());
    }   

    @Test
    void deadPatientShouldNotReceiveCare() {
        StatePatient state = new Dead();
        assertFalse(state.canReceiveCare());
    }

    @Test
    void internedPatientShouldReceiveCare() {
        StatePatient state = new Interned();
        assertTrue(state.canReceiveCare());
    }

}
