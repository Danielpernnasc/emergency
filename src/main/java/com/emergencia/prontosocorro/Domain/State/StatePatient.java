package com.emergencia.prontosocorro.Domain.State;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();

    static String valueOf(String upperCase) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'valueOf'");
    }

 
}
