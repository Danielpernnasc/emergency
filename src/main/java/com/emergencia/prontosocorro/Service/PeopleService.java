package com.emergencia.prontosocorro.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;

import java.time.LocalDateTime;
import com.emergencia.prontosocorro.Domain.models.StatusType;


@Service
public class PeopleService {

    private final RepositoryPeople repositorypeople;

    public PeopleService(RepositoryPeople repositorypeople){
            this.repositorypeople = repositorypeople;
    }


    @Transactional
    public void registerDeath(Long id, String cause){
        People people = repositorypeople.findById(id)
            .orElseThrow(() -> new RuntimeException("People not found with id " + id));

            if(people.getStatePatient() == StatusType.MORTO) {
                throw new IllegalStateException("StatePatient must not be null");
            }

              people.setStatusPatient(StatusType.MORTO);
              people.setDeathCause(cause);
              people.setDeathTime(LocalDateTime.now());
    }


    
}