package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Service.DeathService;



@Service
public class PeopleService {
      private final RepositoryPeople repositoryPeople;
      private final DeathService deathService;

    public PeopleService(RepositoryPeople repositoryPeople, DeathService deathService) {
        this.repositoryPeople = repositoryPeople;
        this.deathService = deathService;
    }

    public StatePatient getStatePatientById(Long id) {
        return repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id))
                .getStatePatient();
    }

    public ResponseEntity<Long> updateStatePatient(Long id, StatusType newStatus, String justification, LocalDateTime date) {
        return deathService.updateStatePatient(id, newStatus, justification, date);
    }

    public boolean mistakeStatus(Long id, StatusType newStatus, String justification) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        StatusType currentStatus = people.getStatusPatient();

       if(currentStatus != StatusType.MORTO) {
            return false;
        }
        

        people.changeStatus(newStatus);
        repositoryPeople.save(people);
        return true;
    }
}