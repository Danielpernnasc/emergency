package com.emergencia.prontosocorro.domain.state.status;
import com.emergencia.prontosocorro.domain.state.StatePatient;
import com.emergencia.prontosocorro.domain.enums.StatusType;


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
