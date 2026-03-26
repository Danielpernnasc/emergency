package com.emergencia.prontosocorro.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.domain.entity.Hospital;
import com.emergencia.prontosocorro.infra.event.ProcessedEvent;

@Repository
public interface RepositoryHospital extends JpaRepository<Hospital, Long> {

    List<Hospital> findByNameHospitalContainingIgnoreCase(String name);


}
