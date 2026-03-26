package com.emergencia.prontosocorro.infra.messaging.consumer;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.service.CareService;


@ExtendWith(MockitoExtension.class)
public class HospitalEventConsumerTest {

    @Mock
    private CareService careService;

    @InjectMocks
    private HospitalEventConsumer consumer;

    @Test
    void shouldCallServiceWhenEventReceived(){

        PatientTransferredEvent event = new PatientTransferredEvent(
                "Teste",1L, 1L, 4L
        );

        // act
        consumer.receivePatientTransfer(event);

        // assert
        verify(careService).handleTransfer(event);
    }

    
}
