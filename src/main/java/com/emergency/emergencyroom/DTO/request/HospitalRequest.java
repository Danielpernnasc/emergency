package com.emergency.emergencyroom.DTO.request;

public record HospitalRequest(
    String nameHospital, 
    String address, 
    int number) {
   
    public HospitalRequest() {
        this(null, null, 0);
    }

    
}