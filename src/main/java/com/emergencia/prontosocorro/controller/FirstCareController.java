package com.emergencia.prontosocorro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.DTO.request.ChangeSectorRequest;
import com.emergencia.prontosocorro.DTO.request.DeathRequest;
import com.emergencia.prontosocorro.DTO.request.FirstCareRequest;
import com.emergencia.prontosocorro.DTO.request.StateEvolutionRequest;
import com.emergencia.prontosocorro.DTO.response.FirstCareResponse;
import com.emergencia.prontosocorro.domain.entity.FirstCare;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.domain.entity.People;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.repository.RepositoryHospital;
import com.emergencia.prontosocorro.repository.RepositoryPeople;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;
import com.emergencia.prontosocorro.service.CareService;


@CrossOrigin(origins = "*")
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


    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
        @RequestParam Long patientId,
        @RequestParam Long fromHospital,
        @RequestParam Long toHospital
    ){
        careService.transferPatient(patientId, fromHospital, toHospital);

        return ResponseEntity.ok().build();
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
                firstCare.getCid() != null ? firstCare.getCid().getCode() : null,
                firstCare.getSector()
        );
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

    @PatchMapping("{id}/sector")
    public void changeSector(@PathVariable Long id, @RequestBody ChangeSectorRequest request){
        careService.changeSector(id, request.sector());
    }

}
