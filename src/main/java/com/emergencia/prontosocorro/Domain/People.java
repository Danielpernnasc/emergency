package com.emergencia.prontosocorro.Domain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.State.Status.Dead;
import com.emergencia.prontosocorro.Domain.State.Status.Interned;
import com.emergencia.prontosocorro.Domain.State.Status.Live;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;


@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    int idade;
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_patient", nullable = false)
    private StatusType statusPatient;

    @Transient
    @JsonIgnore
    private StatePatient statePatient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
    private String deathCause;
    @Column(name = "death_date_time")
    private LocalDateTime deathTime;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ComorbidityType> comorbidities;
    
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<FirstCare> firstCares;

    protected People() {
        // obrigatório para JPA
    }

    public People(
        String name,
        int idade,
        String description,
        Hospital hospital,
        List<ComorbidityType> comorbidities
    ) {
        this.name = name;
        this.idade = idade;
        this.description = description;
        this.hospital = hospital;
        this.statusPatient = StatusType.ENFERMO; // 👈 estado inicial
        this.comorbidities = comorbidities != null ? comorbidities : new ArrayList<>();
        syncState();
        this.firstCares = new ArrayList<>();
    }

     @PostLoad
    private void syncState() {
        this.statePatient = switch (this.statusPatient) {
            case ENFERMO -> new Sick();
            case INTERNADO -> new Interned();
            case VIVO -> new Live();
            case MORTO -> new Dead();
            default -> throw new IllegalArgumentException("Unknown status type: " + this.statusPatient);
        };
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
        // Convert StatusType to StatePatient based on the status value
        switch(statusType) {
            case MORTO:
                this.statePatient = new Dead();
                break;
            // Add other cases for other StatusType values
            default:
                throw new IllegalArgumentException("Unknown status type: " + statusType);
        }
    }

    public void setStatePatient(StatePatient statePatient) {
        this.statePatient = statePatient;
    }

    public void registerDeath(String cause) {
        if(this.statePatient instanceof Dead){
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

    public List<FirstCare> getFirstCares() {
        return firstCares;
    }

    public void addFirstCare(FirstCare firstCare) {
        this.firstCares.add(firstCare);
    }

}
