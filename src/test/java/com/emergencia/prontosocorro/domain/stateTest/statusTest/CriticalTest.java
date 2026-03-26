package com.emergencia.prontosocorro.domain.stateTest.statusTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.state.status.Critical;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public class CriticalTest {
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
