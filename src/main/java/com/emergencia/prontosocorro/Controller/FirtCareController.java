package com.emergencia.prontosocorro.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.Controller.DTO.Request.FirstCareRequest;
import com.emergencia.prontosocorro.Controller.DTO.Request.StateEvolutionRequest;
import com.emergencia.prontosocorro.Controller.DTO.Response.FirstCareResponse;
import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Repository.RepositoryFirstCare;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;
import com.emergencia.prontosocorro.Repository.RepositoryPeople;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;
import com.emergencia.prontosocorro.Service.CareService;

@RestController
@RequestMapping("firstcare")
public class FirtCareController {

    private final CareService careService;
    private final RepositoryPeople repositoryPeople;
    private final RepositoryHospital repositoryHospital;
    private final RepositoryFirstCare repositoryFirstCare;
    private final RepositoryCID repositoryCID;
    
    public FirtCareController(
            CareService careService,
            RepositoryPeople repositoryPeople,
            RepositoryHospital repositoryHospital,
            RepositoryCID repositoryCID,
            RepositoryFirstCare repositoryFirstCare) {
        this.careService = careService;
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
        this.repositoryCID = repositoryCID;
        this.repositoryFirstCare = repositoryFirstCare;
    }

    @PostMapping
      public FirstCare create(@RequestBody FirstCareRequest req) {

        People people = repositoryPeople.findById(req.peopleId())
                .orElseThrow(() -> new RuntimeException("People not found"));

        Hospital hospital = repositoryHospital.findById(req.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

    CID cid = null;

    if (req.cidCode() != null) {
        cid = repositoryCID.findById(req.cidCode())  // 😈🔥
                .orElseThrow(() -> new RuntimeException("CID not found"));

        // ✅ Severidade automática via CID
        people.setSeverity(cid.getSeverityLevel());
        people.changeStatus(cid.getSeverityLevel());

        repositoryPeople.save(people);
    }

        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(people);
        firstCare.setHospital(hospital);
        firstCare.setCid(cid);
        firstCare.setSpecialistMedic(req.specialistMedic());
        firstCare.setCareStatus(req.careStatus());
       

        return repositoryFirstCare.save(firstCare);
    }

    @GetMapping
    public List<FirstCareResponse> findAll() {
        return repositoryFirstCare.findAll()
                .stream()
                .map(fc -> new FirstCareResponse(
                        fc.getId(),
                        fc.getCareStatus(),
                        fc.getSpecialistMedic(),
                        fc.getCareDateTime(),
                        fc.getComorbidities() != null ? new ArrayList<>(fc.getComorbidities()) : new ArrayList<>(),
                        fc.getPeople().getId(),
                        fc.getPeople().getName(),
                        fc.getHospital().getId(),
                        fc.getHospital().getNameHospital(), 
                        fc.getCid() != null ? fc.getCid().getCode() : null))
                .toList();
    }

    private FirstCareResponse responsePatiente(FirstCare firstCare) {
        return new FirstCareResponse(
                firstCare.getId(),
                firstCare.getCareStatus(),
                firstCare.getSpecialistMedic(),
                firstCare.getCareDateTime(),
                firstCare.getComorbidities() != null ? new ArrayList<>(firstCare.getComorbidities()) : new ArrayList<>(),
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

    @PutMapping("{id}/evolution")
    public FirstCareResponse updateEvolution(
            @PathVariable Long id,
            @RequestBody StateEvolutionRequest requestEvolution) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));

        Set<CareofPacients> procedures = requestEvolution.procedure();
        careService.applyProcedures(firstCare, procedures);
        repositoryFirstCare.save(firstCare);
        return responsePatiente(firstCare);
    }

    

}
