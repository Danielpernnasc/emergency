package com.emergencia.prontosocorro.DTO.Request;

public record HospitalRequest(
    String nameHospital, 
    String address, 
    int numero) {
   
    public HospitalRequest() {
        this(null, null, 0);
    }

    public String getNameHospital(){
        return nameHospital;
    }

    public String getAddress(){
        return address;
    }

    public int getNumero(){
        return numero;
    }
    
}