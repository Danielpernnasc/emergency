package com.emergencia.prontosocorro.infra.messaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Service.CareService;
import com.emergencia.prontosocorro.infra.Config.RabbitMQConfig;
import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;

@Component
public class HospitalEventConsumer {


    private CareService careService;
    public HospitalEventConsumer(CareService careService){
        this.careService = careService;
    }
    @RabbitListener(queues =  RabbitMQConfig.QUEUE)
    public void receivePatientTransfer(PatientTransferredEvent event){
        careService.handleTransfer(event);


    }

}
