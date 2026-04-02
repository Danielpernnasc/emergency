package com.emergency.emergencyRoom.infra.event;

import java.time.LocalDateTime;

import com.emergency.emergencyRoom.domain.enums.CareSector;

public class SectorChangedEvent {
    
    private String eventId; 
    private Long patientId;
    private CareSector from;
    private CareSector to;

    public SectorChangedEvent(){}

    public SectorChangedEvent(String eventId, Long patientId, CareSector from, CareSector to){
        this.eventId = eventId;
        this.patientId = patientId;
        this.from = from;
        this.to = to;

    }

    public static ProcessedEvent of(String eventId) {
        return new ProcessedEvent(eventId, LocalDateTime.now());
    }

    public String getEventString() { return eventId; }
    public long getPatientId() { return patientId; }
    public CareSector getFrom() { return from; }
    public CareSector getTo() { return to; }

}
