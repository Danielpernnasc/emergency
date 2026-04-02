package com.emergency.emergencyroom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emergency.emergencyroom.DTO.request.HospitalRequest;
import com.emergency.emergencyroom.domain.entity.Hospital;
import com.emergency.emergencyroom.repository.RepositoryHospital;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceTest {

    @Mock
    RepositoryHospital repositoryHospital;

    @InjectMocks
    HospitalService hospitalService;

    @Test
    void shouldcreate(){
        Hospital hospital = new Hospital(
             1L,
             "Hospital Central",
             "Avenida Central",
             100
        );

        when(repositoryHospital.save(any(Hospital.class)))
            .thenReturn(hospital);

         Hospital result = hospitalService.createNameHospital(
            new HospitalRequest(
              "Hospital Central",
              "Avenida Central",
              100
            )
        );

        assertEquals("Hospital Central", result.getNameHospital());
        assertEquals("Avenida Central", result.getAddress());
        assertEquals(100, result.getNumero());
      
    }

    @Test
    void shouldsearchByName(){
        Hospital hospital = new Hospital(
             1L,
             "Hospital Central",
             "Avenida Central",
             100
        );

        when(repositoryHospital.findByNameHospitalContainingIgnoreCase(anyString()))
            .thenReturn(List.of(hospital));

        var result = hospitalService.searchByName("Central");

        assertEquals(1, result.size());
        assertEquals("Hospital Central", result.get(0).getNameHospital());
    }   

    @Test
    void shouldgetAllHospitals(){
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("Hospital Central");
        hospital.setAddress("Avenida Central");
        hospital.setNumero(100);

        when(repositoryHospital.findAll()).thenReturn(List.of(hospital));
        var result = hospitalService.getAllHospitals();
        assertEquals(1, result.size());
        assertEquals("Hospital Central", result.get(0).getNameHospital());
        assertEquals("Avenida Central", result.get(0).getAddress());
        assertEquals(100, result.get(0).getNumero());

    }


    @Test
    void shouldfindById(){
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("Hospital Central");
        hospital.setAddress("Avenida Central");
        hospital.setNumero(100);
        
        when(repositoryHospital.findById(anyLong())).thenReturn(java.util.Optional.of(hospital));
        var result = hospitalService.findById(1L);
        assertEquals("Hospital Central", result.getNameHospital());
        assertEquals("Avenida Central", result.getAddress());
        assertEquals(100, result.getNumero());  
    }

    @Test
    void shouldupdateHospital(){
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setNameHospital("Hospital Central");
        hospital.setAddress("Avenida Central");
        hospital.setNumero(100);
        when(repositoryHospital.findById(anyLong())).thenReturn(java.util.Optional.of(hospital));
        when(repositoryHospital.save(any(Hospital.class))).thenReturn(hospital);
        var result = hospitalService.updateHospital(1L, new HospitalRequest(
            "Hospital Central",
            "Avenida Central",
            100
        ));
        assertEquals("Hospital Central", result.getNameHospital());
        assertEquals("Avenida Central", result.getAddress());
        assertEquals(100, result.getNumero());
    }



}
