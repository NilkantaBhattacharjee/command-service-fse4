package com.cts.skilltracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.entities.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

}
