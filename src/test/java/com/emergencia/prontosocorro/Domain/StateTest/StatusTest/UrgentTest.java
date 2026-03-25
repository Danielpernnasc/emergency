package com.emergencia.prontosocorro.Domain.StateTest.StatusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.status.Urgent;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public class UrgentTest {

    @Test
    void urgentPatientShouldReceiveCare() {
        // Arrange
        Urgent urgent = new Urgent();

        // Act
        boolean canReceiveCare = urgent.canReceiveCare();

        // Assert
        assertTrue(canReceiveCare);
    }

    @Test
    void urgentPatientToStringShouldReturnUrgent(){

        Urgent urgent = new Urgent();

        String result = urgent.toString();

        assertEquals("Urgente", result);
    }

    @Test
    void urgentPatientGetStatusTypeShouldReturnUrgente() {

        Urgent urgent = new Urgent();

        var statusType = urgent.getStatusType();

        assertEquals(StatusType.URGENTE, statusType);
    }

}
