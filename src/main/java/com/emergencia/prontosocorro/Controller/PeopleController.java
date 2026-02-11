package com.emergencia.prontosocorro.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.Controller.DTO.Request.PeopleRequest;
import com.emergencia.prontosocorro.Controller.DTO.Request.StatePatientRequest;
import com.emergencia.prontosocorro.Controller.DTO.Response.PeopleResponse;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Service.DeathService;
import com.emergencia.prontosocorro.Service.PeopleService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;
    private final PeopleService peopleService;
  

    public PeopleController(RepositoryPeople repositoryPeople, RepositoryHospital repositoryHospital, PeopleService peopleService) {
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
        this.peopleService = peopleService;
    
    }

    @PostMapping
    People create(@RequestBody PeopleRequest peopleRequest) {
     
        Hospital hospital = repositoryHospital.findById(peopleRequest.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + peopleRequest.hospitalId()));
        
  
        People people = new People();
        people.setName(peopleRequest.name());
        people.setAge(peopleRequest.idade());
        people.setDescription(peopleRequest.description());
        people.setHospital(hospital);
        people.changeStatus(StatusType.ENFERMO);
        return repositoryPeople.save(people);
    }

    @GetMapping
    public List<PeopleResponse> findAll() {
        return repositoryPeople.findAll()
                .stream()
                .map(pe -> new PeopleResponse(
                        pe.getId(),
                        pe.getName(),
                        pe.getAge(),
                        pe.getDescription(),
                        pe.getStatusPatient(),
                        pe.getHospital() != null ? pe.getHospital().getId() : null,
                        pe.getHospital() != null ? pe.getHospital().getNameHospital() : null,
                        pe.getComorbidities()
                ))
                      
                      
                .toList();
    }

    @GetMapping("{id}")
    public People findById(@PathVariable Long id) {
        return repositoryPeople.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Long> updateState(@PathVariable Long id, @RequestBody StatePatientRequest requestPeople){
        peopleService.updateStatePatient(
            id, 
            requestPeople.statusType(),
            requestPeople.justification(),
            requestPeople.date()
        );
        return ResponseEntity.ok(id);
    }



    @PutMapping("/{id}/state/mistake")
    public ResponseEntity<Long> mistakeStatus(@PathVariable Long id, @RequestBody StatePatientRequest requestPeople) {
        if (requestPeople.justification() == null || requestPeople.justification().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = peopleService.mistakeStatus(id, requestPeople.statusType(), requestPeople.justification());
        if (result) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(409).build(); // Conflito, não permitido   
        }
    }
    

}
