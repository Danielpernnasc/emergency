package com.emergencia.prontosocorro.Domain.StateTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.State.Status.Critical;
import com.emergencia.prontosocorro.Domain.State.Status.Dead;
import com.emergencia.prontosocorro.Domain.State.Status.Interned;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Domain.State.Status.Urgent;

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
