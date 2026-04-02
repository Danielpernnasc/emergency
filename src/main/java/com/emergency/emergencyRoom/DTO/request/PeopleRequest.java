package com.emergency.emergencyRoom.DTO.request;
import java.util.List;

import com.emergency.emergencyRoom.domain.enums.ComorbidityType;
import com.emergency.emergencyRoom.domain.enums.SeverityLevel;

public record PeopleRequest(
    String name,
    int age,
    String description,
    Long hospitalId,
    SeverityLevel severityLevel,
    List<ComorbidityType> comorbidities


) {

    public PeopleRequest() {
        this(null, 0, null, null, null, null);
    }
        
}
