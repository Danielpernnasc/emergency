package com.emergency.emergencyRoom.DTO.request;

public record HospitalRequest(
    String nameHospital, 
    String address, 
    int number) {
   
    public HospitalRequest() {
        this(null, null, 0);
    }

    
}