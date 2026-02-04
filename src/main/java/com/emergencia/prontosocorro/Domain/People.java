package com.emergencia.prontosocorro.Domain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.State.Status.Dead;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;

public class People {
    String name;
    int idade;
    String description;
    StatePatient statePatient;
    Hospital hospital;
    private String deathCause;
    private LocalDateTime deathTime;
    private List<ComorbidityType> comorbidities;

    public People(String name, int idade, String description, StatePatient statePatient, Hospital hospital, List<ComorbidityType> comorbidities) {
        this.name = name;
        this.idade = idade;
        this.description = description;
        this.statePatient = statePatient;
        this.hospital = hospital;
        this.deathCause = null;
        this.deathTime = null;
        this.comorbidities = (comorbidities != null) ? comorbidities : new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return idade;
    }

    public void setAge(int idade) {
        this.idade = idade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatePatient getStatePatient() {
        return statePatient;
    }
    
    public void changeStatus(StatusType statusType) {
        this.statePatient.getStatusType();
    }

    public void setStatePatient(StatePatient statePatient) {
        this.statePatient = statePatient;
    }

    public void registerDeath(String cause) {
        if(this.statePatient.getStatusType() == StatusType.MORTO){
            throw new IllegalStateException("Patient is already registered as dead");
        }
        this.statePatient = new Dead();
        this.deathCause = cause;
        this.deathTime = LocalDateTime.now();
    }

    public String getDeathCause() {
        return deathCause;
    }

    public LocalDateTime getDeathTime() {
        return deathTime;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<ComorbidityType> getComorbidities() {
        return comorbidities == null ? new ArrayList<>() : comorbidities;
    }

    public void setComorbidities(List<ComorbidityType> comorbidities) {
        this.comorbidities = comorbidities;
    }

}
