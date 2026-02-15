package com.emergencia.prontosocorro.Repository.LoaderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emergencia.prontosocorro.Domain.Entity.CIDSubCategory;



@Repository
public interface RepositoryCIDSubCategory extends JpaRepository<CIDSubCategory, String> {

}
