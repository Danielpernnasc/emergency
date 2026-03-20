package com.emergencia.prontosocorro.Message.event;

public class PatientTransferredEvent {
    private Long patientId;
    private Long fromHospitalId;
    private Long toHospitalId;

    public PatientTransferredEvent() {}

    public PatientTransferredEvent(Long patientId, Long fromHospitalId, Long toHospitalId){
        this.patientId = patientId;
        this.fromHospitalId = fromHospitalId;
        this.toHospitalId = toHospitalId;
    }

    public Long getPatientId() { return patientId; }
    public Long getFromHospitalId() { return fromHospitalId; }
    public Long getToHospitalId() { return toHospitalId; }

}
