package com.emergencia.prontosocorro.Message.producer;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.Message.event.SectorChangedEvent;



@Service
public class HospitalEventProducer {

   private final RabbitTemplate rabbitTemplate;
   
   public HospitalEventProducer(RabbitTemplate rabbitTemplate){
     this.rabbitTemplate = rabbitTemplate;
   }

    public void sendPatientTransfer(PatientTransferredEvent event) {

        rabbitTemplate.convertAndSend(
                "hospital.exchange",
                "patient.transfer",
                event
        );

        System.out.println("📤 Evento enviado: paciente " + event.getPatientId());
    }

    public void sendPatienttoSector(SectorChangedEvent event){
      
        rabbitTemplate.convertAndSend(
                "hospital.exchange",
                "patient.transfer",
                event
        );

        System.out.println("📤 Evento enviado: paciente " + event.getPatientId());

      }

}
