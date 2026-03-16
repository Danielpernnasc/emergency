package com.emergencia.prontosocorro.Domain.State;
import com.emergencia.prontosocorro.Domain.enums.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();


 
}
