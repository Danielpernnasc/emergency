package com.emergency.emergencyroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emergency.emergencyroom.DTO.request.HospitalRequest;
import com.emergency.emergencyroom.domain.entity.Hospital;
import com.emergency.emergencyroom.repository.RepositoryHospital;


@Service
public class HospitalService {

    private final RepositoryHospital repositoryHospital;

    public HospitalService(RepositoryHospital repositoryHospital) {
        this.repositoryHospital = repositoryHospital;
    }

    
   public Hospital createNameHospital(HospitalRequest request) {

        Hospital hospital = new Hospital();
            hospital.setNameHospital(request.nameHospital());
            hospital.setAddress(request.address());
            hospital.setNumero(request.number());

        Hospital saved = repositoryHospital.save(hospital);
        return saved;
    }



    public List<Hospital> searchByName(String name) {
        return repositoryHospital.findByNameHospitalContainingIgnoreCase(name);
    }

    public List<Hospital> getAllHospitals() {
        return repositoryHospital.findAll();
    }

    public Hospital findById(Long id) {
        return repositoryHospital.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + id));
    }

    public Hospital updateHospital(Long id, HospitalRequest request) {
        Hospital hospital = repositoryHospital.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id " + id));

        hospital.setNameHospital(request.nameHospital());
        hospital.setAddress(request.address());
        hospital.setNumero(request.number());

        return repositoryHospital.save(hospital);
    }


    



}
