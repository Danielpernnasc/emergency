package com.emergencia.prontosocorro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.DTO.request.PeopleRequest;
import com.emergencia.prontosocorro.DTO.request.StatePatientRequest;
import com.emergencia.prontosocorro.DTO.response.PeopleResponse;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.repository.RepositoryPeople;
import com.emergencia.prontosocorro.service.PeopleService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/people")
public class PeopleController {

 
    private final RepositoryPeople repositoryPeople;
    private final PeopleService peopleService;

  

    public PeopleController(RepositoryPeople repositoryPeople, PeopleService peopleService) {
        this.repositoryPeople = repositoryPeople;
        this.peopleService = peopleService;

    }

    @PostMapping
    public ResponseEntity<People> createPatient(@RequestBody PeopleRequest req) {

        People people = new People();
        people.setName(req.name());
        people.setAge(req.idade());
        people.setDescription(req.description());
        people.setSeverity(req.severityLevel());
        people.setComorbidities(req.comorbidities());

        People createdPeople = peopleService.createPatient(people, req);
        return ResponseEntity.ok(createdPeople);
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
        return peopleService.getStatePatientById(id);
    
    }


    @PutMapping("/{id}/state/mistake")
    public ResponseEntity<Long> mistakeStatus(
        @PathVariable Long id, 
        @RequestBody 
        StatePatientRequest requestPeople) {
    
        boolean result = peopleService.mistakeStatus(id, requestPeople.statusType(), requestPeople.justification());
        if (result) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(409).build();
        }
    }
    

}
