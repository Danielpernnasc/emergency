package com.emergency.emergencyRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyRoom.domain.entity.People;

@Repository
public interface RepositoryPeople extends JpaRepository<People, Long> {

}
