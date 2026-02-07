package com.emergencia.prontosocorro.Controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.Controller.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.Controller.DTO.Response.FirstCareResponse;
import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Service.CareService;

@RestController
@RequestMapping("firstcare")
public class FirtCareController {

    private final CareService careService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;
    private final RepositoryFirstCare  repositoryFirsCare;

    public FirtCareController(
        CareService careService, 
        RepositoryPeople repositoryPeople, 
        RepositoryHospital repositoryHospital,
        RepositoryFirstCare repositoryFirsCare) {
        this.careService = careService;
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
        this.repositoryFirsCare = repositoryFirsCare;
    }

    @PostMapping
    public FirstCare create(@RequestBody FirstCareRequest firstCareRequest) {
       People people  =  repositoryPeople.findById(firstCareRequest.peopleId())
                .orElseThrow(() -> new RuntimeException("People not found with id " + firstCareRequest.peopleId()));
        
        Hospital hospital = repositoryHospital.findById(firstCareRequest.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + firstCareRequest.hospitalId()));
        
   
        FirstCare firstCare = new FirstCare(
            people,
            hospital,
            null, 
            firstCareRequest.specialistMedic()
        );

 
        
        return repositoryFirsCare.save(firstCare);
    }

    @GetMapping
    public List<FirstCareResponse> findAll() {
        return repositoryFirsCare.findAll()
        .stream()
        .map(fc -> new FirstCareResponse(
            fc.getId(),
            fc.getCareStatus(), // ✅ nome correto
            fc.getSpecialistMedic(),
            fc.getCareDateTime(),
            fc.getPeople().getId(),
            fc.getPeople().getName(),
            fc.getHospital().getId(),
            fc.getHospital().getNameHospital()
        ))
        .toList();
    }

    @GetMapping("{id}")
    public FirstCare findById(@PathVariable Long id) {
        return repositoryFirsCare.findById(id)
            .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));
    }

}
