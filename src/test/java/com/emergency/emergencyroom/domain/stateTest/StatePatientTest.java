package com.emergency.emergencyroom.domain.stateTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.domain.state.StatePatient;
import com.emergency.emergencyroom.domain.state.status.Critical;
import com.emergency.emergencyroom.domain.state.status.Dead;
import com.emergency.emergencyroom.domain.state.status.Interned;
import com.emergency.emergencyroom.domain.state.status.Sick;
import com.emergency.emergencyroom.domain.state.status.Urgent;

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
