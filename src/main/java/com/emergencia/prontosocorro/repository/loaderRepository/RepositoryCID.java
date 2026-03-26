package com.emergencia.prontosocorro.repository.loaderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.domain.entity.CID;

@Repository
public interface RepositoryCID extends JpaRepository<CID, String> {

}
