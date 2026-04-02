package com.emergency.emergencyroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyroom.domain.entity.CID;

@Repository
public interface RepositoryCID extends JpaRepository<CID, String> {

}
