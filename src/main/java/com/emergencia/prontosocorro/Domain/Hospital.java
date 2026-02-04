package com.emergencia.prontosocorro.Domain;

public class Hospital {
    Long id;
    String name;
    String address;
    int numero;

    public Hospital(Long id, String name, String address, int numero) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numero = numero;
    }
    /** Getters and Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameHospital() {
        return name;
    }

    public void setNameHospital(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}

