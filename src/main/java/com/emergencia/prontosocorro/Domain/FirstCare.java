package com.emergencia.prontosocorro.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

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


@Entity
public class FirstCare {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "hospital_id")
        private Hospital hospital;
        @ManyToOne
        @JoinColumn(name = "people_id")
        private People patient;
        @Enumerated(EnumType.STRING)
        private SpecialistMedic specialistMedic;
        @Enumerated(EnumType.STRING)
        private CareStatus carestatus;
        @Column(name = "care_date_time")
        private LocalDateTime careDateTime;
    
        @ElementCollection(targetClass = CareofPacients.class)
        @CollectionTable(
            name = "first_care_procedures",
            joinColumns = @JoinColumn(name = "first_care_id")
        )
        @Enumerated(EnumType.STRING)
        @Column(name = "procedure")
        private List<CareofPacients> procedures = new ArrayList<>();

        public FirstCare() {
            // obrigatório para JPA
        }
        public FirstCare(Hospital hospital, People patient, SpecialistMedic specialistMedic) {
            this.hospital = hospital;
            this.patient = patient;
            this.specialistMedic = specialistMedic;
            this.carestatus = CareStatus.EM_ATENDIMENTO;
            this.careDateTime = LocalDateTime.now();
            this.procedures = new ArrayList<>();
        }


        /** Getters and Setters */

        public Hospital getHospital() {
            return hospital;
        }

        public void setHospital(Hospital hospital) {
            this.hospital = hospital;
        }

        public People getPeople() {
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

        public CareStatus getCarestatus() {
            return carestatus;
        }
        public void setCarestatus(CareStatus carestatus) {
            this.carestatus = carestatus;
        }

        public List<CareofPacients> getProcedures() {
            return procedures;
        }


        public void addProcedures(CareofPacients careofPacients) {
            this.procedures.add(careofPacients);
        }
       

        public void disCharge() {
            if(patient.getStatePatient().getStatusType().getState().equals("morto")) {
                  throw new IllegalStateException("Paciente morto não recebe alta");
            }
            this.carestatus = CareStatus.ALTA;
        }




}
