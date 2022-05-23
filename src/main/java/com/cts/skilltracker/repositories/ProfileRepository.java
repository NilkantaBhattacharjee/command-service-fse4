package com.cts.skilltracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.entities.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
	
	Optional<ProfileEntity> findByAssociateId(String associateId);

}
