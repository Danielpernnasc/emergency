package com.emergencia.prontosocorro.Repository.LoaderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDCategory;

@Repository
public interface RepositoryCID extends JpaRepository<CID, String> {

    void save(CIDCategory category);

}
