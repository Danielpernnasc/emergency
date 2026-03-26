package com.emergencia.prontosocorro.domain.state;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();


 
}
