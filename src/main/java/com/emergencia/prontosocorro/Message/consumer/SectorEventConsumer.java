package com.emergencia.prontosocorro.Message.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Message.Config.RabbitMQConfig;
import com.emergencia.prontosocorro.Message.event.SectorChangedEvent;
import com.emergencia.prontosocorro.Service.CareService;

@Component
public class SectorEventConsumer {

    private CareService careService;

    public SectorEventConsumer(CareService careService){
        this.careService = careService;
    }

    @RabbitListener(queues =  RabbitMQConfig.QUEUE)
        public void receivePatientTransfer(SectorChangedEvent event){

            careService.handleTransferSector(event);

        }



}
