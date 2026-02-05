package com.emergencia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.People;

@Repository
public interface RepositoryPeople extends JpaRepository<People, Long> {

}
