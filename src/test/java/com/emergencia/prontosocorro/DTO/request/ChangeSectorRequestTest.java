package com.emergencia.prontosocorro.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.DTO.request.ChangeSectorRequest;
import com.emergencia.prontosocorro.domain.enums.CareSector;

public class ChangeSectorRequestTest {

    @Test
    void soudlChangeSectorRequest(){
        ChangeSectorRequest sectorRequest = new ChangeSectorRequest(
            CareSector.TRIAGEM
        );

        assertEquals(CareSector.TRIAGEM, sectorRequest.sector());
    }

}
