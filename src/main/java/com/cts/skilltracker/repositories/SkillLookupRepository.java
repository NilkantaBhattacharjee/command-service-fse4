package com.cts.skilltracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.entities.SkillLookupEntity;

public interface SkillLookupRepository extends JpaRepository<SkillLookupEntity, Integer> {

	Optional<SkillLookupEntity> findByName(String name);

}
