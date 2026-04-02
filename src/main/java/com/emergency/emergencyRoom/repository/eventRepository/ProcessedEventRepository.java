package com.emergency.emergencyRoom.repository.eventRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergency.emergencyRoom.infra.event.ProcessedEvent;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {

}
