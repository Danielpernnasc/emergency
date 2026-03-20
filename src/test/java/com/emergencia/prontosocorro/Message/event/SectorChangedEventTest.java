package com.emergencia.prontosocorro.Message.event;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.enums.CareSector;

public class SectorChangedEventTest {

    @Test
    void shouldSectorChangedEvent(){

        SectorChangedEvent sectorChangedEventJPA = new SectorChangedEvent();


        SectorChangedEvent sectorChangedEvent = new SectorChangedEvent(
            1L,
            CareSector.CENTRO_CIRURGICO,
            CareSector.SETOR_CTI
        );

        assertNotNull(sectorChangedEventJPA);
        assertEquals(1L, sectorChangedEvent.getPatientId());
        assertEquals(CareSector.CENTRO_CIRURGICO, sectorChangedEvent.getFrom());
        assertEquals(CareSector.SETOR_CTI, sectorChangedEvent.getTo());

        
    }

}
