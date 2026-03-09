package com.emergencia.prontosocorro.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_hospital", nullable = false)
    private String nameHospital;

    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "numero", nullable = false)
    private int numero;

    public Hospital() {
        // obrigatório para JPA
    }

    public Hospital(Long id, String nameHospital, String address, int numero) {
        this.id = id;
        this.nameHospital = nameHospital;
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
        return nameHospital;
    }

    public void setNameHospital(String nameHospital) {
        this.nameHospital = nameHospital;
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

