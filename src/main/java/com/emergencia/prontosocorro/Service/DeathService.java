package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;

@Service
public class DeathService {

    private final RepositoryPeople repositoryPeople;


    public DeathService(RepositoryPeople repositoryPeople) {
        this.repositoryPeople = repositoryPeople;

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

        LocalDateTime deathTime =  date != null ? date : LocalDateTime.now();

        people.registerDeath(justification, deathTime);

        repositoryPeople.save(people);
    }


}
