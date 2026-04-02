package com.emergency.emergencyroom.infra.event;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessedEvent {
    @Id
    private String eventId;

   @Column(nullable = false)
    private LocalDateTime processedAt;

    protected ProcessedEvent(){}

    public ProcessedEvent(
        String eventId, 
        LocalDateTime processedAt
    ){
        this.eventId = eventId;
        this.processedAt = processedAt;
    }

    public static ProcessedEvent of(String eventId) {
        return new ProcessedEvent(eventId, LocalDateTime.now());
    }

    public String getEventId() {
        return eventId;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }




}
