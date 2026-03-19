package com.emergencia.prontosocorro.Message.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Message.Config.RabbitMQConfig;
import com.emergencia.prontosocorro.Message.event.PatientTransferredEvent;

@Component
public class HospitalEventConsumer {

    @RabbitListener(queues =  RabbitMQConfig.QUEUE)
    public void receivePatientTransfer(PatientTransferredEvent event){

        System.out.println("📥 Paciente recebido:");
        System.out.println("Paciente ID: " + event.getPatientId());
        System.out.println("De hospital: " + event.getFromHospitalId());
        System.out.println("Para hospital: " + event.getToHospitalId());


    }

 

    

}
