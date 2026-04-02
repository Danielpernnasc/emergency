package com.emergency.emergencyroom.infra.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergency.emergencyroom.infra.config.RabbitMQConfig;
import com.emergency.emergencyroom.infra.event.PatientTransferredEvent;
import com.emergency.emergencyroom.service.CareService;

@Component
public class HospitalEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(HospitalEventConsumer.class);
    
    private CareService careService;
    public HospitalEventConsumer(CareService careService){
        this.careService = careService;
    }


    @RabbitListener(queues =  RabbitMQConfig.QUEUE)
    public void receivePatientTransfer(PatientTransferredEvent event){

        try {

            log.info("Receive event - Evento recebido: {}", event.getEventId());

            careService.handleTransfer(event);
        } catch(Exception e ){
            log.error("Error processing event - Erro ao processar o evento: {}", event.getEventId(), e);
            throw e;
        }


    }

}
