package com.emergency.emergencyRoom.infra.messaging.consumer;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergency.emergencyRoom.domain.enums.CareSector;
import com.emergency.emergencyRoom.infra.event.SectorChangedEvent;
import com.emergency.emergencyRoom.infra.messaging.consumer.SectorEventConsumer;
import com.emergency.emergencyRoom.service.CareService;

@ExtendWith(MockitoExtension.class)
public class SectorEventConsumerTest {

    @Mock
    private CareService careService;

    @InjectMocks
    private SectorEventConsumer consumer;

     @Test
    void shouldCallServiceWhenSectorEventReceived(){

        // Arrange
        SectorChangedEvent event = new SectorChangedEvent(
                UUID.randomUUID().toString(),
                1L,
                CareSector.TRIAGEM,
                CareSector.CONSULTORIO
        );

        // Act
        consumer.receivePatientTransfer(event);

        // Assert
        verify(careService).handleTransferSector(event);
    }
}
