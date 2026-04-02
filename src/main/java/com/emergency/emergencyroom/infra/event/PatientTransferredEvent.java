package com.emergency.emergencyroom.infra.event;



public class PatientTransferredEvent {
    private String eventId;
    private Long patientId;
    private Long fromHospitalId;
    private Long toHospitalId;

    public PatientTransferredEvent() {}

    public PatientTransferredEvent(String eventId, Long patientId, Long fromHospitalId, Long toHospitalId){
        this.eventId = eventId;
        this.patientId = patientId;
        this.fromHospitalId = fromHospitalId;
        this.toHospitalId = toHospitalId;
    }


    public String getEventId() { return eventId; }
    public Long getPatientId() { return patientId; }
    public Long getFromHospitalId() { return fromHospitalId; }
    public Long getToHospitalId() { return toHospitalId; }

}
