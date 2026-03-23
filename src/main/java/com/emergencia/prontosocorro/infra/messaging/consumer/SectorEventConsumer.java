package com.emergencia.prontosocorro.infra.messaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Service.CareService;
import com.emergencia.prontosocorro.infra.Config.RabbitMQConfig;
import com.emergencia.prontosocorro.infra.event.SectorChangedEvent;

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
