package com.emergencia.prontosocorro.Message.consumer;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.Service.CareService;

@ExtendWith(MockitoExtension.class)
public class HospitalEventConsumerTest {

    @Mock
    private CareService careService;

    @InjectMocks
    private HospitalEventConsumer consumer;

    @Test
    void shouldCallServiceWhenEventReceived(){

        PatientTransferredEvent event = new PatientTransferredEvent(
                1L, 1L, 4L
        );

        // act
        consumer.receivePatientTransfer(event);

        // assert
        verify(careService).handleTransfer(event);
    }

    
}
