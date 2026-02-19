package com.emergencia.prontosocorro.Domain.State;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();


 
}
