package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;

@Service
public class PeopleService {
      private final RepositoryPeople repositoryPeople;

    public PeopleService(RepositoryPeople repositoryPeople) {
        this.repositoryPeople = repositoryPeople;
    }

    public StatePatient getStatePatientById(Long id) {
        return repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id))
                .getStatePatient();
    }

    public ResponseEntity<Long> updateStatePatient(Long id, StatusType newStatus, String justification, LocalDateTime date) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        StatusType currentStatus = people.getStatusPatient();

        if (newStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        
        if (currentStatus == StatusType.MORTO && newStatus != StatusType.MORTO) {
            return ResponseEntity.status(409).build();
        }
        if (currentStatus == StatusType.INTERNADO && newStatus == StatusType.ENFERMO) {
            return ResponseEntity.ok(id);
        }
        if (newStatus == StatusType.MORTO) {
            if (justification == null || justification.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            LocalDateTime deathTime = date != null ? date : LocalDateTime.now();
            people.registerDeath(justification, deathTime);
            repositoryPeople.save(people);
            return ResponseEntity.ok(id);
        }

        people.changeStatus(newStatus);
        repositoryPeople.save(people);
        return ResponseEntity.ok(id);
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