package com.emergencia.prontosocorro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.domain.entity.People;

@Repository
public interface RepositoryPeople extends JpaRepository<People, Long> {

}
