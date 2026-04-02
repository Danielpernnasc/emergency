package com.emergency.emergencyRoom.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.status.Critical;

 class CriticalTest {
    @Test
    void criticalPatientShouldReceiveCare() {
        // Arrange
        Critical critical = new Critical();

        // Act
        boolean canReceiveCare = critical.canReceiveCare();

        // Assert
        assertTrue(canReceiveCare);
    }

    @Test
    void criticalPatientToStringShouldReturnCritical() {
        // Arrange
        Critical critical = new Critical();

        // Act
        String result = critical.toString();

        // Assert
        assertEquals("Crítico", result);
    }

    @Test
    void criticalPatientGetStatusTypeShouldReturnCritico() {
        // Arrange
        Critical critical = new Critical();

        // Act
        var statusType = critical.getStatusType();

        // Assert
        assertEquals(StatusType.CRITICO, statusType);
    }

}
