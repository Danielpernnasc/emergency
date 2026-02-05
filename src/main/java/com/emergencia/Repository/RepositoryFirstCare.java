package com.emergencia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.FirstCare;

@Repository
public interface RepositoryFirstCare extends JpaRepository<FirstCare, Long> {

}
