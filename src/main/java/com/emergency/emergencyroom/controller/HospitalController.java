package com.emergency.emergencyroom.controller;

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

import com.emergency.emergencyroom.DTO.request.HospitalRequest;
import com.emergency.emergencyroom.domain.entity.Hospital;
import com.emergency.emergencyroom.repository.RepositoryHospital;
import com.emergency.emergencyroom.service.HospitalService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("hospital")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    public  ResponseEntity<Hospital> create(@RequestBody HospitalRequest request){
       
        Hospital saved = hospitalService.createNameHospital(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

     @GetMapping("{id}")
    public Hospital findById(@PathVariable Long id){
        try {
            return hospitalService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Hospital not found with id " + id);
        }
    }

   
    @GetMapping("/search/{name}")
    public List<Hospital> searchByName(@PathVariable String name) {
        return hospitalService.searchByName(name);
    }

    @PutMapping("{id}")
    public ResponseEntity<Hospital> update(@PathVariable Long id, @RequestBody HospitalRequest request) {
        try {
            Hospital updatedHospital = hospitalService.updateHospital(id, request);
            return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

 

}
