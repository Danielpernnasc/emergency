package com.emergencia.prontosocorro.infra.event;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.enums.CareSector;


public class SectorChangedEventTest {

    @Test
    void shouldSectorChangedEvent(){

        SectorChangedEvent sectorChangedEventJPA = new SectorChangedEvent();


        SectorChangedEvent sectorChangedEvent = new SectorChangedEvent(
            UUID.randomUUID().toString(),
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
