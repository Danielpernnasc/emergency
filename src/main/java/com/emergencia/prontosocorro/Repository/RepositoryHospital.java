package com.emergencia.prontosocorro.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emergencia.prontosocorro.Domain.Hospital;

@Repository
public interface RepositoryHospital extends JpaRepository<Hospital, Long> {

    List<Hospital> findByNameHospitalContainingIgnoreCase(String name);

}
