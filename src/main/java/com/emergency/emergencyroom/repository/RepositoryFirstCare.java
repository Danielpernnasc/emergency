package com.emergency.emergencyroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyroom.domain.entity.FirstCare;
import com.emergency.emergencyroom.domain.entity.People;


@Repository
public interface RepositoryFirstCare extends JpaRepository<FirstCare, Long> {

    Optional<People> findAllById(Long cidId);




}
