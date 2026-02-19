package com.emergencia.prontosocorro.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.StatusType;
import com.emergencia.prontosocorro.Service.CareService;
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
import jakarta.persistence.PrePersist;


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
    private StatusType statusPatient = StatusType.ENFERMO; //✅🔥


    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private String deathCause;
    @Column(name = "death_date_time")
    private LocalDateTime deathTime;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "comorbidities", length = 50)
    private List<ComorbidityType> comorbidities;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private SeverityLevel severity;


    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<FirstCare> firstCares;

    public People() {
        // obrigatório para JPA
    }

      public People(String name, int idade, String description, Hospital hospital, StatusType statusPatient, SeverityLevel severity) {
        this.name = name;
        this.idade = idade;
        this.description = description;
        this.hospital = hospital;
        this.statusPatient = statusPatient;
        this.severity = severity;

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

    @PrePersist
    public void prePersist() {
        if (statusPatient == null)
            statusPatient = StatusType.ENFERMO;
        if (severity == null)
            severity = SeverityLevel.LEVE;
    }


    public void changeStatus(StatusType statusType) {

    if (statusType == null) {
        throw new IllegalArgumentException("StatusType is required");
    }

        this.statusPatient = statusType;
    }

  public void registerDeath(String cause, LocalDateTime deathTime) {

    if (this.statusPatient == StatusType.MORTO) {
        throw new IllegalStateException("Patient already dead");
    }

    this.statusPatient = StatusType.MORTO;
    this.deathCause = cause;
    this.deathTime = deathTime != null ? deathTime : LocalDateTime.now();
}

    public StatusType getStatusPatient() {
        return statusPatient;
    }

   

    public void clearDeathInfo() {
        this.deathCause = null;
        this.deathTime = null;
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

    public SeverityLevel getSeverity() {
        return severity;
    }



    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;

    }

}
