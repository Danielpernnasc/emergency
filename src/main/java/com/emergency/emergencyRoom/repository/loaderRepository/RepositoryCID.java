package com.emergency.emergencyRoom.repository.loaderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emergency.emergencyRoom.domain.entity.CID;

@Repository
public interface RepositoryCID extends JpaRepository<CID, String> {

}
