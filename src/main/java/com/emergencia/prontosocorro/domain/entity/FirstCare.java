package com.emergencia.prontosocorro.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.emergencia.prontosocorro.domain.enums.CareSector;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.CareofPacients;
import com.emergencia.prontosocorro.domain.enums.ComorbidityType;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.domain.enums.StatusType;

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
import jakarta.persistence.PrePersist;

@Entity
public class FirstCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cid_code")
    private CID cid;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @ManyToOne
   @JoinColumn(name = "people_id", nullable = false)
    private People patient;

    @Enumerated(EnumType.STRING)
    private SpecialistMedic specialistMedic;

    @Enumerated(EnumType.STRING)
    private CareStatus careStatus;

    @Column(name = "care_date_time", nullable = false)
    private LocalDateTime careDateTime;

    @Enumerated(EnumType.STRING)
    private ComorbidityType comorbidityType;

    @ElementCollection(targetClass = CareofPacients.class)
    @CollectionTable(name = "first_care_procedures", joinColumns = @JoinColumn(name = "first_care_id"))

    @Enumerated(EnumType.STRING)
    @Column(name = "care_procedure")
    private Set<CareofPacients> procedures = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private CareSector sector;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

  public FirstCare() {
    // Required by JPA
    }

    public FirstCare(People patient, CID cid, Hospital hospital, SpecialistMedic specialist_medics, CareStatus care_status, CareSector sector, SeverityLevel severity) {
        this.patient = patient;
        this.cid = cid;
        this.hospital = hospital;
        this.specialistMedic =  specialist_medics;
        this.careStatus = care_status;
        this.careDateTime = LocalDateTime.now();
        this.procedures = new HashSet<>();
        this.sector = sector;
        this.severity = severity;
    }
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public People getPeople() {
        return patient;
    }

    public People setPeople(People patient) {
        this.patient = patient;
        return patient;
    }

    public SpecialistMedic getSpecialistMedic() {
        return specialistMedic;
    }

    public void setSpecialistMedic(SpecialistMedic specialistMedic) {
        this.specialistMedic = specialistMedic;
    }


    public LocalDateTime getCareDateTime() {
        return careDateTime;
    }

    @PrePersist
    public void prePersist() {
        if (careDateTime == null) {
            careDateTime = LocalDateTime.now();
        }
    }

    public CareStatus getCareStatus() {
        return careStatus;
    }

    public void setCareStatus(CareStatus careStatus) {
        this.careStatus = careStatus;
    }

    public Set<CareofPacients> getProcedures() {
        return procedures;
    }

    public void setProcedures(Set<CareofPacients> procedures) {
        this.procedures = procedures;
    }

    public void setComorbidityType(ComorbidityType comorbidityType) {
        this.comorbidityType = comorbidityType;
    }

    public void addProcedures(CareofPacients careofPacients) {
        this.procedures.add(careofPacients);
    }

    public void disCharge() {
        if (patient.getStatusPatient().equals(StatusType.MORTO)) {
            throw new IllegalStateException("Paciente morto não recebe alta");
        }
        this.careStatus = CareStatus.DE_ALTA;
    }

    public CID getCid() {
        return cid;
    }

    public void setCid(CID cid) {
        this.cid = cid;
    }

    public CareSector getSector(){
        return sector;
    }

    public void setSector(CareSector sector){
        this.sector = sector;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

}
