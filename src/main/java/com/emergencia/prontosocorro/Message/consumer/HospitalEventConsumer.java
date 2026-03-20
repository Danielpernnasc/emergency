package com.emergencia.prontosocorro.Message.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Message.Config.RabbitMQConfig;
import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.Service.CareService;

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
