package com.emergencia.prontosocorro.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;




@Service
public class PeopleService {
      private final RepositoryPeople repositoryPeople;
      private final DeathService deathService;

    public PeopleService(RepositoryPeople repositoryPeople, DeathService deathService) {
        this.repositoryPeople = repositoryPeople;
        this.deathService = deathService;
    }

    public StatusType getStatePatientById(Long id) {
        return repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id))
                .getStatusPatient();
    }

    public void registerDeath(Long id, String justification, LocalDateTime date) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        deathService.registerDeath(people, justification, date);
    }

    public boolean mistakeStatus(Long id, StatusType newStatus, String justification) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        StatusType currentStatus = people.getStatusPatient();

       if(currentStatus != StatusType.MORTO) {
            return false;
        }

        people.setStatusPatient(newStatus);
        if (newStatus == StatusType.ENFERMO || newStatus == StatusType.INTERNADO) {
            people.clearDeathInfo();
        }
        repositoryPeople.save(people);
        return true;
    }

   public void updateState(Long id, SeverityLevel severityLevel, String justification) {
        People people = repositoryPeople.findById(id)
            .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        people.changeStatus(severityLevel);
        people.setSeverity(severityLevel);

        repositoryPeople.save(people);
    }
}

  