package com.emergency.emergencyRoom.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyRoom.domain.entity.Hospital;
import com.emergency.emergencyRoom.infra.event.ProcessedEvent;

@Repository
public interface RepositoryHospital extends JpaRepository<Hospital, Long> {

    List<Hospital> findByNameHospitalContainingIgnoreCase(String name);


}
