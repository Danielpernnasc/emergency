package com.emergency.emergencyroom.infra.observability;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;

@Component
public class ObservabilityService {

    private final MeterRegistry meterRegistry;
    
    public ObservabilityService(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }

    
    private static final String CREATE_METRIC = "patient.create.total";
    private static final String UPDATE_METRIC = "patient.update.total";
    private static final String TRANSFER_METRIC = "patient.transfer.total";
    private static final String DEATH_METRIC = "patient.death.total";

    public void incrementCreateCounter(){
        meterRegistry.counter(CREATE_METRIC, "status", "success").increment();
    }

    public void incrementUpdateCounter(){
        meterRegistry.counter(UPDATE_METRIC, "status", "success").increment();
    }
     
    public void incrementTransferCounter(){
        meterRegistry.counter(TRANSFER_METRIC,"status", "success").increment();
    }

    public void incrementDeathCounter(){
        meterRegistry.counter(DEATH_METRIC, "status", "success").increment();
    }

   @PostConstruct
    public void testMetric() {
        meterRegistry.counter("patient.create.total").increment();
    }


}
