package com.emergencia.prontosocorro.Service;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;


@Service
public class DecisionCaseCriticalService {

    public boolean isCriticalCase(People people) {
        String description = people.getDescription().toLowerCase();

        return description.contains("infarto") ||
               description.contains("trauma perfurante")
               || description.contains("AVC");
    }

    public boolean patientNoResist(People people) {
        if(people.getDescription().toLowerCase().contains("infarto")
           && people.getAge() > 45 && 
        people.getComorbidities().contains(ComorbidityType.HIPERTENSAO) ||
        people.getComorbidities().contains(ComorbidityType.DIABETES)
        || people.getComorbidities().contains(ComorbidityType.OBESIDADE)
        || people.getComorbidities().contains(ComorbidityType.DOENCA_DE_CHAGAS)
        ) {
            return true;
        }

        if(people.getDescription().toLowerCase().contains("trauma perfurante")
             && people.getAge() > 45 && people.getComorbidities().contains(ComorbidityType.DIABETES)
              || people.getComorbidities().contains(ComorbidityType.HIPERTENSAO) 
              || people.getComorbidities().contains(ComorbidityType.CARDIOPATIA)
        ) {
            return true;
        }

        if(people.getDescription().toLowerCase().contains("AVC")
             && people.getAge() > 45 && people.getComorbidities().contains(ComorbidityType.HIPERTENSAO)
              || people.getComorbidities().contains(ComorbidityType.DIABETES)
        ) {
            return true;
        }
        return false;
    }
}
