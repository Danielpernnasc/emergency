package com.emergencia.prontosocorro.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.emergencia.prontosocorro.Controller.DTO.Request.PeopleRequest;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Controller.DTO.Request.DeathRequest;


import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;

    public PeopleController(RepositoryPeople repositoryPeople, RepositoryHospital repositoryHospital) {
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
    }

    @PostMapping
    People create(@RequestBody PeopleRequest peopleRequest) {
     
        Hospital hospital = repositoryHospital.findById(peopleRequest.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + peopleRequest.hospitalId()));
        
        People people = new People(
            peopleRequest.name(),
            peopleRequest.idade(),
            peopleRequest.description(),
            hospital,
            peopleRequest.comorbidities()
        );
        return repositoryPeople.save(people);
    }

    @GetMapping
    public List<People> findAll() {
        return repositoryPeople.findAll();
    }

    @GetMapping("{id}")
    public People findById(@PathVariable Long id) {
        return repositoryPeople.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
    }

  
    

}
