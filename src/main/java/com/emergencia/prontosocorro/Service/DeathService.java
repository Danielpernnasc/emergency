package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.Entity.People;
import com.emergencia.prontosocorro.Domain.enums.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.infra.observability.ObservabilityService;

@Service
public class DeathService {

    private final RepositoryPeople repositoryPeople;
    private final ObservabilityService observabilityService;


    public DeathService(RepositoryPeople repositoryPeople, ObservabilityService observabilityService) {
        this.repositoryPeople = repositoryPeople;
        this.observabilityService = observabilityService;
    }

    public void registerDeath(
        People people,
        String justification,
        LocalDateTime date
    ){
        if(people.getStatusPatient() == StatusType.MORTO){
            throw new IllegalStateException("Patient already dead");
        }

        if(justification == null || justification.isBlank()){
            throw new IllegalStateException("justification is mandatadory");
        }

       observabilityService.incrementDeathRegister();
        LocalDateTime deathTime =  date != null ? date : LocalDateTime.now();

        people.registerDeath(justification, deathTime);

        repositoryPeople.save(people);
    }


}
