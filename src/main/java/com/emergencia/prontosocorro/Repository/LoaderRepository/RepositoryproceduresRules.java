package com.emergencia.prontosocorro.Repository.LoaderRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.Entity.CIDProceduresRules;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;

@Repository
public interface RepositoryproceduresRules extends JpaRepository<CIDProceduresRules, Long> {

    List<CareofPacients> findByGroup_Code(String code);

}
