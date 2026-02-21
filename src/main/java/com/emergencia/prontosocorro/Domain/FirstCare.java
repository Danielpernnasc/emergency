package com.emergencia.prontosocorro.Domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;

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
    @JoinColumn(name = "people_id")
    private People patient;

    @Enumerated(EnumType.STRING)
    private SpecialistMedic specialistMedic;

    @Enumerated(EnumType.STRING)
    private CareStatus careStatus;

    @Column(name = "care_date_time", nullable = false)
    private LocalDateTime careDateTime;

    @Enumerated(EnumType.STRING)
    private ComorbidityType comorbidityType;

    @ElementCollection
    @CollectionTable(name = "first_care_cares_status", joinColumns = @JoinColumn(name = "first_care_id"))

    @Enumerated(EnumType.STRING)
    @Column(name = "care_status")
    private Set<CareStatus> care_status = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "first_care_specialist_medics", joinColumns = @JoinColumn(name = "first_care_id"))

    @Enumerated(EnumType.STRING)
    @Column(name = "specialist_medic")
    private Set<SpecialistMedic> specialist_medics = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "first_care_comorbidities", joinColumns = @JoinColumn(name = "first_care_id"))

    @Enumerated(EnumType.STRING)
    @Column(name = "comorbidity")
    private Set<ComorbidityType> comorbidities = new HashSet<>();

    @ElementCollection(targetClass = CareofPacients.class)
    @CollectionTable(name = "first_care_procedures", joinColumns = @JoinColumn(name = "first_care_id"))

    @Enumerated(EnumType.STRING)
    @Column(name = "care_procedure")
    private Set<CareofPacients> procedures = new HashSet<>();

  public FirstCare() {
    // Required by JPA
    }

    public FirstCare(People patient, CID cid, Hospital hospital, Set<SpecialistMedic> specialist_medics, Set<ComorbidityType> comorbidities, Set<CareStatus> care_status) {
        this.hospital = hospital;
        this.patient = patient;
        this.specialist_medics = specialist_medics != null ? new HashSet<>(specialist_medics) : new HashSet<>();
        this.care_status = care_status != null ? new HashSet<>(care_status) : new HashSet<>();
        this.careDateTime = LocalDateTime.now();
        this.comorbidities = comorbidities != null ? new HashSet<>(comorbidities) : new HashSet<>();
        this.procedures = new HashSet<>();
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

    public void setSpecialist_Medic(Set<SpecialistMedic> specialist_medics) {
        this.specialist_medics = specialist_medics != null ? new HashSet<>(specialist_medics) : new HashSet<>();
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

    public void setCare_Status(Set<CareStatus> care_status) {
        this.care_status = care_status != null ? new HashSet<>(care_status) : new HashSet<>();
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

    public Set<ComorbidityType> getComorbidities() {
        return comorbidities;
    }

    public void setComorbidities(Set<ComorbidityType> comorbidities) {
        this.comorbidities = comorbidities != null ? new HashSet<>(comorbidities) : new HashSet<>();
    }

    public void addProcedures(CareofPacients careofPacients) {
        this.procedures.add(careofPacients);
    }

    public void disCharge() {
        if (patient.getStatusPatient().equals(StatusType.MORTO)) {
            throw new IllegalStateException("Paciente morto não recebe alta");
        }
        this.careStatus = CareStatus.ALTA;
    }

    public CID getCid() {
        return cid;
    }

    public void setCid(CID cid) {
        this.cid = cid;
    }

}
