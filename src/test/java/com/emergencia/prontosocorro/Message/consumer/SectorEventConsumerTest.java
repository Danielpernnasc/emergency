package com.emergencia.prontosocorro.Message.consumer;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Message.event.SectorChangedEvent;
import com.emergencia.prontosocorro.Service.CareService;

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
