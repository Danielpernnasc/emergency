package com.emergencia.prontosocorro.Repository.EventRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergencia.prontosocorro.infra.event.ProcessedEvent;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {

}
