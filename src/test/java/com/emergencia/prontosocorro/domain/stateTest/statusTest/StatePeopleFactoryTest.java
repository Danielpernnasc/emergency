package com.emergencia.prontosocorro.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.StatePatient;
import com.emergencia.prontosocorro.domain.state.status.Critical;
import com.emergencia.prontosocorro.domain.state.status.Dead;
import com.emergencia.prontosocorro.domain.state.status.Interned;
import com.emergencia.prontosocorro.domain.state.status.Sick;
import com.emergencia.prontosocorro.domain.state.status.StatePeopleFactory;
import com.emergencia.prontosocorro.domain.state.status.Urgent;
import com.emergencia.prontosocorro.domain.enums.StatusType;

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
