package com.emergency.emergencyroom.domain.state;
import com.emergency.emergencyroom.domain.enums.StatusType;

public interface StatePatient {

    boolean canReceiveCare();

    StatusType getStatusType();


 
}
