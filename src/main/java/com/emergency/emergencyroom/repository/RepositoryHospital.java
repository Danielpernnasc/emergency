package com.emergency.emergencyroom.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyroom.domain.entity.Hospital;


@Repository
public interface RepositoryHospital extends JpaRepository<Hospital, Long> {

    List<Hospital> findByNameHospitalContainingIgnoreCase(String name);


}
