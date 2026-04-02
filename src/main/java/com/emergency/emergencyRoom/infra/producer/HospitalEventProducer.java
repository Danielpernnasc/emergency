package com.emergency.emergencyRoom.infra.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.emergency.emergencyRoom.infra.config.RabbitMQConfig;
import com.emergency.emergencyRoom.infra.event.PatientTransferredEvent;
import com.emergency.emergencyRoom.infra.event.SectorChangedEvent;
import com.emergency.emergencyRoom.infra.messaging.consumer.HospitalEventConsumer;

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
           RabbitMQConfig.EXCHANGE,
           RabbitMQConfig.ROUTING_KEY,
           event
    );
  }

    // 🔥 FALLBACK
    public void fallbackSend(PatientTransferredEvent event, Throwable ex) {
       throw new RuntimeException("Erro ao enviar evento para RabbitMQ: " + ex.getMessage()); 
    }

   

    public void sendPatienttoSector(SectorChangedEvent event){
      
        rabbitTemplate.convertAndSend(
               RabbitMQConfig.EXCHANGE,
               RabbitMQConfig.ROUTING_KEY,  
               event
        );

        log.info("📤 Evento setor enviado {}", event.getEventString());

      }

}
