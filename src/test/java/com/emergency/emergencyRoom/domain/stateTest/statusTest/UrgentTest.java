package com.emergency.emergencyRoom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.status.Urgent;

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
