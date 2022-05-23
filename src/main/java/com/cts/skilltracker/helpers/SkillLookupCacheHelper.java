package com.cts.skilltracker.helpers;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.entities.SkillLookupEntity;
import com.cts.skilltracker.repositories.SkillLookupRepository;

@Component
public class SkillLookupCacheHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SkillLookupCacheHelper.class);
	
	@Autowired
	SkillLookupRepository lkupRepository;
	
	@Cacheable(value = "allskillcache")
	public List<SkillLookupEntity> fetchSkills() {
		
		String METHOD = "fetchSkills() - ";
		logger.info(METHOD + "Entry -> Fetching skills from database...");
		
		List<SkillLookupEntity> skillList = null;
		skillList = lkupRepository.findAll();
		if(skillList!=null && skillList.size() > 0) {
			logger.info(METHOD + "Number of skills in database:: " + skillList.size());
		}
		
		logger.info(METHOD + "Exit");
		return skillList;
		
	}
	
	@Cacheable(value = "skillcache" , key = "#name")
	public SkillLookupEntity fetchSkillByName(String name) {
		
		String METHOD = "fetchSkillByName() - ";
		logger.info(METHOD + "Entry -> Fetching skill for name:: " + name);
		
		SkillLookupEntity entity = null;
		logger.info(METHOD + "Calling database...");
		Optional<SkillLookupEntity> entityOpt = lkupRepository.findByName(name);
		if(entityOpt.isPresent()) {
			entity = entityOpt.get();
		} else {
			logger.info(METHOD + "Skill does not exist:: " + name);
		}
		
		logger.info(METHOD + "Exit");
		return entity;
		
	}

}
