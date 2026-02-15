package com.emergencia.prontosocorro.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

@Configuration
public class DatabaseSeeder {
    @Bean
    CommandLineRunner loadCidDataBase(RepositoryCID repo){
        return args -> {
            if (repo.count() > 0) return; 
                repo.save(new CID(
                      "R50",                      
                    "Febre",
                    SeverityLevel.MODERADO,
                    SpecialistMedic.CLINICAL_MEDICINE
                    
)); System.out.println("✅ CIDs carregados"); }; } }