package com.emergencia.prontosocorro.Domain;

import java.time.LocalDateTime;

import com.emergencia.prontosocorro.Domain.models.CareStatus;

public class FirstCare {

        private Hospital hospital;
        private People patient;
        private SpecialistMedic specialistMedic;
        private CareStatus carestatus;
        private LocalDateTime careDateTime;


        public FirstCare(Hospital hospital, People patient, SpecialistMedic specialistMedic) {
            this.hospital = hospital;
            this.patient = patient;
            this.specialistMedic = specialistMedic;
            this.carestatus = CareStatus.EM_ATENDIMENTO;
            this.careDateTime = LocalDateTime.now();
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

        public void disCharge() {
            if(patient.getStatePatient().getStatusType().getState().equals("morto")) {
                  throw new IllegalStateException("Paciente morto não recebe alta");
            }
            this.carestatus = CareStatus.ALTA;
        }


}
