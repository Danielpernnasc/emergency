package com.emergencia.prontosocorro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

@SpringBootApplication
public class ProntosocorroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProntosocorroApplication.class, args);
    }

}
