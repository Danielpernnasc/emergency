package com.emergency.emergencyRoom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyRoom.domain.enums.CareSector;

class ChangeSectorRequestTest {

    @Test
    void soudlChangeSectorRequest(){
        ChangeSectorRequest sectorRequest = new ChangeSectorRequest(
            CareSector.TRIAGEM
        );

        assertEquals(CareSector.TRIAGEM, sectorRequest.sector());
    }

}
