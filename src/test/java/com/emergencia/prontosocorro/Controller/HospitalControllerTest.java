package com.emergencia.prontosocorro.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.emergencia.prontosocorro.DTO.Request.HospitalRequest;
import com.emergencia.prontosocorro.Domain.Entity.Hospital;
import com.emergencia.prontosocorro.Repository.RepositoryHospital;

@ExtendWith(MockitoExtension.class)
public class HospitalControllerTest {

    @Mock
    RepositoryHospital repositoryHospital;

    @InjectMocks
    HospitalController hospitalController;

    @Test
    void shouldcreate(){
        HospitalRequest hospitalRequest = new HospitalRequest(
            "Hospital Central",
            "Avenida Central",
            100
        );

        Hospital hospital = new Hospital();
        hospital.setNameHospital("Hospital Central");
        hospital.setAddress("Avenida Central");
        hospital.setNumero(100);

           when(repositoryHospital.save(any(Hospital.class))).thenReturn(hospital);

        ResponseEntity<Hospital> response = hospitalController.create(hospitalRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Hospital Central", response.getBody().getNameHospital());
        assertEquals("Avenida Central", response.getBody().getAddress());
        assertEquals(100, response.getBody().getNumero());
    }
    
    @Test
    void shouldAllHospitals(){
        Hospital hospital = new Hospital(
              1L,
              "Hospital Central",
              "Avenida Central",
              100
        );

     when(repositoryHospital.findAll()).thenReturn(List.of(hospital));
    List<Hospital> result = hospitalController.getAllHospitals();


    assertEquals(1, result.size());
     assertEquals("Hospital Central", hospital.getNameHospital());
     assertEquals( "Avenida Central", hospital.getAddress());
     assertEquals(100, hospital.getNumero());
    }


    @Test
    void shouldFindById(){
        Hospital hospital = new Hospital(
             1L,
                "Hospital Central",
              "Avenida Central",
              100
     
        );

         when(repositoryHospital.findById(1L)).thenReturn(Optional.of(hospital));

        Hospital result = hospitalController.findById(1L);

        assertEquals(1L, result.getId());

    }

    @Test
    void shouldSearchByName(){
        Hospital hospital = new Hospital(
             1L,
             "Hospital Central",
             "Avenida Central",
             100
        );

        when(repositoryHospital.findByNameHospitalContainingIgnoreCase("Hospital Central")).thenReturn(List.of(hospital));

        List<Hospital> result = hospitalController.searchByName("Hospital Central");

         assertEquals(1, result.size());
         assertEquals("Hospital Central", result.get(0).getNameHospital());
        }


        @Test
        void shouldUpdate(){

            Hospital req = new  Hospital(
                  1L,
                 "Hospital Central",
                "Avenida Central",
                100
            );

            req.setId(1L);
            req.setNameHospital("Hospital Central");
            req.setAddress("Avenida Central");
            req.setNumero(100);

            when(repositoryHospital.findById(1L)).thenReturn(Optional.of(req));
            HospitalRequest reqHospital = new HospitalRequest(
                   "Hospital Central",
                "Avenida Central",
                100
            );
            ResponseEntity<Hospital> result = hospitalController.update(1L, reqHospital);

            assertEquals(null, result.getBody());
            
            
        }
}


   



