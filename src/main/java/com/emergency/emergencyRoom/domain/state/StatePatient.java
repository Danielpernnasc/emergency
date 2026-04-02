package com.emergency.emergencyRoom.domain.state;
import com.emergency.emergencyRoom.domain.enums.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();


 
}
