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
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
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
    private final RepositoryFirstCare repositoryFirstCare;

    public FirtCareController(
            CareService careService,
            RepositoryPeople repositoryPeople,
            RepositoryHospital repositoryHospital,
            RepositoryFirstCare repositoryFirstCare) {
        this.careService = careService;
        this.repositoryPeople = repositoryPeople;
        this.repositoryHospital = repositoryHospital;
        this.repositoryFirstCare = repositoryFirstCare;
    }

    @PostMapping
    public FirstCare create(@RequestBody FirstCareRequest firstCareRequest) {
        People people = repositoryPeople.findById(firstCareRequest.peopleId())
                .orElseThrow(() -> new RuntimeException("People not found with id " + firstCareRequest.peopleId()));

        Hospital hospital = repositoryHospital.findById(firstCareRequest.hospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + firstCareRequest.hospitalId()));

        List<ComorbidityType> comorbidities = firstCareRequest.comorbidities();
        if ((comorbidities == null || comorbidities.isEmpty()) && firstCareRequest.getComorbidities() != null) {
            comorbidities = new ArrayList<>();
            comorbidities.addAll(firstCareRequest.getComorbidities());
        }
        if (comorbidities != null && !comorbidities.isEmpty()) {
            people.setComorbidities(comorbidities);
            repositoryPeople.save(people);
        }

        FirstCare firstCare = new FirstCare(
                people,
                hospital,
                firstCareRequest.specialistMedic(),
                comorbidities != null ? new HashSet<>(comorbidities) : new HashSet<>(),
                CareStatus.EM_ATENDIMENTO);

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
                        fc.getHospital().getNameHospital()))
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
                firstCare.getHospital().getNameHospital());
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

    @PutMapping("{id}/discharge")
    public FirstCareResponse discharge(@PathVariable Long id) {
        FirstCare firstCare = repositoryFirstCare.findById(id)
                .orElseThrow(() -> new RuntimeException("FirstCare not found with id " + id));
        careService.dischargePatient(firstCare);
        repositoryFirstCare.save(firstCare);
        if (!careService.canBeDiscarged(firstCare.getPeople(), firstCare)) {
            throw new RuntimeException(
                    "Patient cannot be discharged. Please ensure all necessary procedures are completed.");
        }
        return responsePatiente(firstCare);

    }

}
