package com.emergencia.prontosocorro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergencia.prontosocorro.DTO.request.HospitalRequest;
import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.repository.RepositoryHospital;

import jakarta.persistence.EntityNotFoundException;


@CrossOrigin(origins = "https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app/swagger-ui/index.html")
@RestController
@RequestMapping("hospital")
public class HospitalController {
    private final RepositoryHospital repositoryHospital;

    public HospitalController(RepositoryHospital repositoryHospital) {
        this.repositoryHospital = repositoryHospital;
    }

    @PostMapping
    public  ResponseEntity<Hospital> create(@RequestBody HospitalRequest request){
          Hospital hospital = new Hospital();
    hospital.setNameHospital(request.getNameHospital());
    hospital.setAddress(request.getAddress());
    hospital.setNumero(request.getNumero());

    Hospital saved = repositoryHospital.save(hospital);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return repositoryHospital.findAll();
    }

     @GetMapping("{id}")
    public Hospital findById(@PathVariable Long id){
        return repositoryHospital.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with id " + id));
    }

    @GetMapping("search")
    public List<Hospital> searchByName(@PathVariable String name) {
        return repositoryHospital.findByNameHospitalContainingIgnoreCase(name);
    }

    @PutMapping("{id}")
    public ResponseEntity<Hospital> update(@PathVariable Long id, @RequestBody HospitalRequest request) {
        Hospital hospital = repositoryHospital.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with id " + id));

        hospital.setNameHospital(request.getNameHospital());
        hospital.setAddress(request.getAddress());
        hospital.setNumero(request.getNumero());

        Hospital updated = repositoryHospital.save(hospital);
        return ResponseEntity.ok(updated);
    }

 

}
