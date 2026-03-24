package com.emergencia.prontosocorro.infra.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Service.CareService;
import com.emergencia.prontosocorro.infra.config.RabbitMQConfig;
import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;

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
