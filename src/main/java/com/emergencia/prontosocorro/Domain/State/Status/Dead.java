package com.emergencia.prontosocorro.Domain.State.Status;
import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.enums.StatusType;


public class Dead implements StatePatient {

    @Override
    public boolean canReceiveCare(){
        return false;
    }

    @Override
    public String toString() {
        return "Morto";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.MORTO;
    }

}
