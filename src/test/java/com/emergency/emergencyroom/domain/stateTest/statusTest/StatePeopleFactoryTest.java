package com.emergency.emergencyroom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;
import com.emergency.emergencyroom.domain.state.status.Critical;
import com.emergency.emergencyroom.domain.state.status.Dead;
import com.emergency.emergencyroom.domain.state.status.Interned;
import com.emergency.emergencyroom.domain.state.status.Sick;
import com.emergency.emergencyroom.domain.state.status.StatePeopleFactory;
import com.emergency.emergencyroom.domain.state.status.Urgent;

public class StatePeopleFactoryTest {

    @Test
    void shouldReturnSickState() {
        StatePatient  state = StatePeopleFactory.from(StatusType.ENFERMO);
        assertTrue(state instanceof Sick);

    }

    @Test
    void shouldReturnInternedState() {
        StatePatient  state = StatePeopleFactory.from(StatusType.INTERNADO);
        assertTrue(state instanceof Interned);
     
    }

    @Test
    void shouldReturnCriticalState() {
        StatePatient  state = StatePeopleFactory.from(StatusType.CRITICO);
        assertTrue(state instanceof Critical);
     
    }

    @Test
    void shouldReturnDeadState() {
        StatePatient  state = StatePeopleFactory.from(StatusType.MORTO);
        assertTrue(state instanceof Dead);
        
    }

    @Test
    void shouldReturnUrgentState() {
        StatePatient  state = StatePeopleFactory.from(StatusType.URGENTE);
        assertTrue(state instanceof Urgent);
        
    }

}
