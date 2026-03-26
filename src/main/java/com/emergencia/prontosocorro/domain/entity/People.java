package com.emergencia.prontosocorro.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
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

    @Column(name = "death_date_time")
    private LocalDateTime deathTime;

    @Column(name = "death_cause")
    private String deathCause;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "people_comorbidities",
        joinColumns = @JoinColumn(name = "people_id")
    )
    @Column(name = "comorbidities")
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

    public void changeStatus(SeverityLevel newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("SeverityLevel is required");
        }

        this.severity = newStatus;
        this.statusPatient = switch (newStatus) {
            case GRAVE -> StatusType.CRITICO;
            case MODERADO -> StatusType.URGENTE;
            case LEVE, OUTROS -> StatusType.ENFERMO;
            case UTI -> StatusType.INTERNADO;
            case OBSERVACAO -> StatusType.FORA_PERIGO;
        };
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

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    public void setStatusPatient(StatusType statusPatient) {
        this.statusPatient = statusPatient;
    }

    public void ensureAlive(){
         if (this.getStatusPatient() == StatusType.MORTO) {
            throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Cannot register death for deceased patient");
        }

    }

    public @Nullable Object getDeathTime() {
        return deathTime;
    }
}
