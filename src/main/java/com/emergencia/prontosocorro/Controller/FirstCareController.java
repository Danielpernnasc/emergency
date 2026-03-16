package com.emergencia.prontosocorro.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.DTO.Request.DeathRequest;
import com.emergencia.prontosocorro.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.DTO.Request.StateEvolutionRequest;
import com.emergencia.prontosocorro.DTO.Response.FirstCareResponse;
import com.emergencia.prontosocorro.Domain.Entity.FirstCare;
import com.emergencia.prontosocorro.Domain.Entity.Hospital;
import com.emergencia.prontosocorro.Domain.Entity.People;
import com.emergencia.prontosocorro.Domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;
import com.emergencia.prontosocorro.Service.CareService;

@RestController
@RequestMapping("firstcare")
public class FirstCareController {

    private final CareService careService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;
    private final RepositoryFirstCare repositoryFirstCare;

    
    public FirstCareController(
            CareService careService,
            RepositoryPeople repositoryPeople,
            RepositoryHospital repositoryHospital,
            RepositoryCID repositoryCID,
            RepositoryFirstCare repositoryFirstCare) {
        this.careService = careService;
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
        this.repositoryFirstCare = repositoryFirstCare;
    }

   @PostMapping
    public FirstCare create(@RequestBody FirstCareRequest req) {

        People people = repositoryPeople.findById(req.peopleId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        System.out.println("STATUS BEFORE: " + people.getStatusPatient());

        Hospital hospital = repositoryHospital.findById(req.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        return careService.createFirstCare(people, hospital, req);
    }


    @GetMapping
    public List<FirstCareResponse> findAll() {
          System.out.println("ENTROU NO FIND ALL 🚑");
        return repositoryFirstCare.findAll()
        
                .stream()
                .map(this::responsePatiente)
                .toList();
    }

    private FirstCareResponse responsePatiente(FirstCare firstCare) {

        return new FirstCareResponse(
                firstCare.getId(),
                firstCare.getCareStatus(),
                firstCare.getSpecialistMedic(),
                firstCare.getCareDateTime(),
                firstCare.getPeople().getComorbidities() != null 
                ? new ArrayList<>(firstCare.getPeople().getComorbidities()) 
                : new ArrayList<>(),
                firstCare.getPeople().getId(),
                firstCare.getPeople().getName(),
                firstCare.getHospital().getId(),
                firstCare.getHospital().getNameHospital(),
                firstCare.getCid() != null ? firstCare.getCid().getCode() : null);
          
    }

    @GetMapping("{id}")
    public FirstCareResponse findById(@PathVariable Long id) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));
        return responsePatiente(firstCare);

    }

  

     @PutMapping("{id}/add-comorbidity")
     public FirstCareResponse addComorbidity(
        @PathVariable Long id, 
        @RequestBody List<ComorbidityType> comorbidites) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));
                careService.addComorbidity(firstCare, comorbidites);
                repositoryFirstCare.save(firstCare);
                return responsePatiente(firstCare);
     }
      
     
     
    
    @PutMapping("{id}/evolution")
    public FirstCareResponse updateEvolution(
            @PathVariable Long id,
            @RequestBody StateEvolutionRequest requestEvolution) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id: " + id));
       
        careService.applyProcedures(
            id,
            firstCare,
            requestEvolution.procedure(),
            requestEvolution.careStatus()   
        );

        repositoryFirstCare.save(firstCare);
        return responsePatiente(firstCare);
        //return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/register-death")
    public FirstCareResponse registerDeath(@PathVariable Long id, @RequestBody DeathRequest deathRequest) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));
    

        careService.registerDeath(firstCare, deathRequest.deathCause(), deathRequest.deathTime());

        return responsePatiente(firstCare);
    }

}
