package com.emergencia.prontosocorro.Service;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class CareService {

    public FirstCare createFirstCare(People people, Hospital hospital) {
        // Lógica para criar um atendimento inicial
        if(people == null || hospital == null) {
            throw new IllegalArgumentException("People and Hospital must not be null");
        }

        if(people.getStatePatient() == null){
            throw new IllegalArgumentException("StatePatient must not be null");
        }

        if(!people.getStatePatient().canReceiveCare()) {
            throw new IllegalStateException("Patient is not in a state to receive care");
        }

        if (people.getStatePatient().getStatusType() == StatusType.MORTO) {
            throw new IllegalStateException(
                "Dead people do not receive hospital care"
            );
        }

        RegretsMedicService regretsMedic = new RegretsMedicService(people, hospital);
        SpecialistMedic specialistMedic = regretsMedic.defineSepSpecialistMedic(people);
        return new FirstCare(hospital, people, specialistMedic);
    }

    public void dischargePatient(FirstCare firstCare) {
        if(firstCare == null) {
            throw new IllegalArgumentException("FirstCare must not be null");
        }
        firstCare.disCharge();
    }

    public boolean canBeDiscarge(People people) {
        String desc = people.getDescription().toLowerCase();
        if(desc.contains("febre") || desc.contains("corte")) {
            return true;
        }
        return false;
    }

}
