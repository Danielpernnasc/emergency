package com.emergencia.prontosocorro.infra.event;

import com.emergencia.prontosocorro.Domain.enums.CareSector;

public class SectorChangedEvent {

    private Long patientId;
    private CareSector from;
    private CareSector to;

    public SectorChangedEvent(){}

    public SectorChangedEvent(Long patientId, CareSector from, CareSector to){

        this.patientId = patientId;
        this.from = from;
        this.to = to;

    }

    public long getPatientId() { return patientId; }
    public CareSector getFrom() { return from; }
    public CareSector getTo() { return to; }

}
