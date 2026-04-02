package com.emergency.emergencyRoom.infra.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergency.emergencyRoom.infra.config.RabbitMQConfig;
import com.emergency.emergencyRoom.infra.event.SectorChangedEvent;
import com.emergency.emergencyRoom.service.CareService;

@Component
public class SectorEventConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(HospitalEventConsumer.class);
    private CareService careService;

    public SectorEventConsumer(CareService careService){
        this.careService = careService;
    }

    @RabbitListener(queues =  RabbitMQConfig.QUEUE)
        public void receivePatientTransfer(SectorChangedEvent event){
        log.info("📥 Evento setor recebido {}", event.getEventString());
            careService.handleTransferSector(event);

        }



}
