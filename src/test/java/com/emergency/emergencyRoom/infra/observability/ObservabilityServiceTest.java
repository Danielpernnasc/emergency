package com.emergency.emergencyRoom.infra.observability;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;


public class ObservabilityServiceTest {

    @Mock
    MeterRegistry meterRegitry;

    @Test
    void shouldincrementCreateCounter(){
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        Counter counter = mock(Counter.class);
         
             when(meterRegistry.counter(anyString(), any(), any()))
            .thenReturn(counter);
          ObservabilityService service = new ObservabilityService(meterRegistry);

             service.incrementCreateCounter();

            verify(counter).increment();
                verify(meterRegistry).counter("patient.create.total", "status", "success");
          
    }

    @Test
    void shouldincrementUpdateCounter(){
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        Counter counter = mock(Counter.class);
        when(meterRegistry.counter(anyString(), any(), any()))
            .thenReturn(counter);
          ObservabilityService service = new ObservabilityService(meterRegistry);

             service.incrementUpdateCounter();

            verify(counter).increment();
            verify(meterRegistry).counter("patient.update.total", "status", "success");
    }

    @Test
    void shouldincrementTransferCounter(){
        
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        Counter counter = mock(Counter.class);
        when(meterRegistry.counter(anyString(), any(), any()))
            .thenReturn(counter);
          ObservabilityService service = new ObservabilityService(meterRegistry);

             service.incrementTransferCounter();

            verify(counter).increment();
            verify(meterRegistry).counter("patient.transfer.total", "status", "success");
    }


    @Test
    void shouldincrementDeathCounter(){

        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        Counter counter = mock(Counter.class);
        when(meterRegistry.counter(anyString(), any(), any()))
            .thenReturn(counter);
          ObservabilityService service = new ObservabilityService(meterRegistry);

             service.incrementDeathCounter();

            verify(counter).increment();
            verify(meterRegistry).counter("patient.death.total", "status", "success");
    }

}
