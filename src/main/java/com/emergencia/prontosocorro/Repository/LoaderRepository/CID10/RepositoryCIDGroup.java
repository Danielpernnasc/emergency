package com.emergencia.prontosocorro.Repository.LoaderRepository.CID10;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDGroup;

@Repository
public interface RepositoryCIDGroup extends JpaRepository<CIDGroup, String>  {

}
