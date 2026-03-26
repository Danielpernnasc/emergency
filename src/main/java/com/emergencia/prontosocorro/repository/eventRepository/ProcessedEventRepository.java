package com.emergencia.prontosocorro.repository.eventRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergencia.prontosocorro.infra.event.ProcessedEvent;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {

}
