package com.emergency.emergencyroom.service;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emergency.emergencyroom.DTO.request.PeopleRequest;
import com.emergency.emergencyroom.domain.entity.Hospital;
import com.emergency.emergencyroom.domain.entity.People;
import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.infra.observability.ObservabilityService;
import com.emergency.emergencyroom.repository.RepositoryHospital;
import com.emergency.emergencyroom.repository.RepositoryPeople;


@Service
public class PeopleService {

      private final RepositoryPeople repositoryPeople;
      private final DeathService deathService;
      private final RepositoryHospital repositoryHospital;
     private final ObservabilityService observabilityService;


    public PeopleService(
        RepositoryPeople repositoryPeople, 
        DeathService deathService, 
        RepositoryHospital repositoryHospital,
        ObservabilityService observabilityService
    
    ) {
        this.repositoryPeople = repositoryPeople;
        this.deathService = deathService;
        this.repositoryHospital = repositoryHospital;
        this.observabilityService = observabilityService;
     

    }

    public People createPatient(People people, PeopleRequest reqPeople) {
        observabilityService.incrementCreateCounter();
        Hospital hospital = repositoryHospital.findById(reqPeople.hospitalId())
        .orElseThrow(() -> new RuntimeException(
            "Hospital not found with id " + reqPeople.hospitalId()));
            people.setHospital(hospital);
        people.setStatusPatient(StatusType.ENFERMO);
        return repositoryPeople.save(people);
    }

    public People getStatePatientById(Long id) {
        return repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
    }

    public People updatedPatient(Long id, PeopleRequest updatedPatient) {
        People existingPatient = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));

                Optional.ofNullable(updatedPatient.name())
                .ifPresent(existingPatient::setName);

                Optional.ofNullable(updatedPatient.age())
                .ifPresent(existingPatient::setAge);

                Optional.ofNullable(updatedPatient.description())
                .ifPresent(existingPatient::setDescription);

                Optional.ofNullable(updatedPatient.comorbidities())
                .ifPresent(existingPatient::setComorbidities);

                Optional.ofNullable(updatedPatient.severityLevel())
                .ifPresent(existingPatient::setSeverity);

        return repositoryPeople.save(existingPatient);
    }

    public void registerDeath(Long id, String justification, LocalDateTime date) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));
        deathService.registerDeath(people, justification, date);
    }

    public boolean mistakeStatus(Long id, StatusType newStatus, String justification) {
        People people = repositoryPeople.findById(id)
                .orElseThrow(() -> new RuntimeException("People not found with id " + id));

            if (people.getStatusPatient() != StatusType.MORTO) {
                throw new IllegalStateException("Only dead patients can have status corrected");
            }
              people.setStatusPatient(newStatus);

       
            if (newStatus == StatusType.ENFERMO || newStatus == StatusType.INTERNADO) {
                people.clearDeathInfo();
            }
            
            repositoryPeople.save(people);
            return true;
       
    } 
}

  