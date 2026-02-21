package com.emergencia.prontosocorro.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.People;


@Repository
public interface RepositoryFirstCare extends JpaRepository<FirstCare, Long> {

    Optional<People> findAllById(Long cidId);




}
