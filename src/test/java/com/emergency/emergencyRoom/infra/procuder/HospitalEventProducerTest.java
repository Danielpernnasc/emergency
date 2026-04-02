package com.emergency.emergencyRoom.infra.procuder;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.emergency.emergencyRoom.domain.enums.CareSector;
import com.emergency.emergencyRoom.infra.event.PatientTransferredEvent;
import com.emergency.emergencyRoom.infra.event.SectorChangedEvent;
import com.emergency.emergencyRoom.infra.producer.HospitalEventProducer;

@ExtendWith(MockitoExtension.class)
public class HospitalEventProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private HospitalEventProducer producer;

    @Test
    void shouldHospitalEventProduce(){

        
        PatientTransferredEvent event = new PatientTransferredEvent(
               "Teste", 1L, 1L, 4L
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
            UUID.randomUUID().toString(),
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
