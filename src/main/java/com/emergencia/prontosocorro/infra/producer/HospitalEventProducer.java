package com.emergencia.prontosocorro.infra.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.infra.event.PatientTransferredEvent;
import com.emergencia.prontosocorro.infra.event.SectorChangedEvent;
import com.emergencia.prontosocorro.infra.messaging.consumer.HospitalEventConsumer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class HospitalEventProducer {

   private final RabbitTemplate rabbitTemplate;
   private static final Logger log = LoggerFactory.getLogger(HospitalEventConsumer.class);

   public HospitalEventProducer(RabbitTemplate rabbitTemplate){
     this.rabbitTemplate = rabbitTemplate;
   }

   @CircuitBreaker(name = "rabbitMq", fallbackMethod = "fallbackSend")
   public void sendPatientTransfer(PatientTransferredEvent event) {

    log.info("📤 Enviando evento {} para paciente {}",
        event.getEventId(), event.getPatientId());

    rabbitTemplate.convertAndSend(
            "hospital.exchange",
            "patient.transfer",
            event
    );
  }

    // 🔥 FALLBACK
    public void fallbackSend(PatientTransferredEvent event, Throwable ex) {
        System.out.println("⚠️ Rabbit fora do ar, fallback ativado");
    }

   

    public void sendPatienttoSector(SectorChangedEvent event){
      
        rabbitTemplate.convertAndSend(
                "hospital.exchange",
                "patient.transfer",
                event
        );

        log.info("📤 Evento setor enviado {}", event.getEventString());

      }

}
