package com.emergency.emergencyroom.repository.eventRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergency.emergencyroom.infra.event.ProcessedEvent;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {

}
