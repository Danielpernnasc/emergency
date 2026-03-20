package com.emergencia.prontosocorro.Message.procuder;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.Message.event.SectorChangedEvent;
import com.emergencia.prontosocorro.Message.producer.HospitalEventProducer;

@ExtendWith(MockitoExtension.class)
public class HospitalEventProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private HospitalEventProducer producer;

    @Test
    void shouldHospitalEventProduce(){

        
        PatientTransferredEvent event = new PatientTransferredEvent(
                1L, 1L, 4L
        );

        producer.sendPatientTransfer(event);

        verify(rabbitTemplate).convertAndSend(
            "hospital.exchange",
            "patient.transfer",
            event
        );
    }

    @Test
    void shouldSendSectorChangedEvent(){
        SectorChangedEvent sectorChangedEvent = new SectorChangedEvent(
            1L,
            CareSector.TRIAGEM,
            CareSector.CONSULTORIO
        );

        producer.sendPatienttoSector(sectorChangedEvent);

        verify(rabbitTemplate).convertAndSend(
              "hospital.exchange",
            "patient.transfer",
            sectorChangedEvent
        );
    }

}
