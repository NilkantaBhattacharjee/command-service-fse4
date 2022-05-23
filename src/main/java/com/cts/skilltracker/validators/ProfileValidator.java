package com.cts.skilltracker.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.entities.ProfileEntity;
import com.cts.skilltracker.entities.SkillLookupEntity;
import com.cts.skilltracker.helpers.SkillLookupCacheHelper;
import com.cts.skilltracker.repositories.ProfileRepository;

@Component
public class ProfileValidator {
	
	@Autowired
	SkillLookupCacheHelper helper;
	
	@Autowired
	ProfileRepository profileRepo;
	
	/**
	 * This method is used to validate whether associate already exists or not
	 * @param associateId
	 * @return assocExistsFlag
	 */
	public boolean doesAssociateExist(String associateId) {
		
		boolean assocExistsFlag = false;
		
		Optional<ProfileEntity> opt = profileRepo.findByAssociateId(associateId);
		if(opt.isPresent()) {
			assocExistsFlag = true;
		}
		
		return assocExistsFlag;
	}
	
	/**
	 * This method is used to validate the skill name being passed in request
	 * @param skillMap
	 * @return strDiff
	 */
	public String validateSkillName(Map<String, Integer> skillMap) {
		
		List<String> reqNameList = new ArrayList<>(skillMap.keySet());
		List<String> skillNameList = helper.fetchSkills().stream().map(SkillLookupEntity::getName).collect(Collectors.toList());
		
		List<String> differences = reqNameList.stream().filter(element -> !skillNameList.contains(element)).collect(Collectors.toList());
		
		String strDiff = String.join(",", differences);
		
		return strDiff;
		
	}
	
	/**
	 * This method is used to validate the expertise level
	 * @param skillMap
	 * @return invalidFlag
	 */
	public boolean validateLevel(Map<String, Integer> skillMap) {
		
		boolean invalidFlag = false;
		List<Integer> values = new ArrayList<>(skillMap.values());
		for(Integer value:values) {
			if(value <0 || value > 20) {
				 invalidFlag = true;
				 break;
			}
		}
		return invalidFlag;
	}

}
