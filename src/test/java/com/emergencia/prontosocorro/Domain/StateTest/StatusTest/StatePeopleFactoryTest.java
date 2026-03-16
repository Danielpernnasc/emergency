package com.emergencia.prontosocorro.Domain.StateTest.StatusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.State.Status.Critical;
import com.emergencia.prontosocorro.Domain.State.Status.Dead;
import com.emergencia.prontosocorro.Domain.State.Status.Interned;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Domain.State.Status.StatePeopleFactory;
import com.emergencia.prontosocorro.Domain.State.Status.Urgent;
import com.emergencia.prontosocorro.Domain.enums.StatusType;

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
