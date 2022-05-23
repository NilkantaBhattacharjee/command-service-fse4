package com.cts.skilltracker.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.aggregates.ProfileAggregate;
import com.cts.skilltracker.entities.ProfileEntity;
import com.cts.skilltracker.entities.SkillEntity;
import com.cts.skilltracker.entities.SkillLookupEntity;
import com.cts.skilltracker.events.BaseEvent;
import com.cts.skilltracker.helpers.SkillLookupCacheHelper;
import com.cts.skilltracker.repositories.ProfileRepository;

@Component
public class ProfileEntityManager {

	private static Logger logger = LoggerFactory.getLogger(ProfileEntityManager.class);

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	SkillLookupCacheHelper cacheHelper;

	@Autowired
	@Qualifier("profileAggregateEventSourcingRepository")
	private EventSourcingRepository<ProfileAggregate> profileAggregateESRepository;

	@EventSourcingHandler
	void on(BaseEvent<?> event) {
		persistProfile(buildQueryProfile(getProfileFromEvent(event)));
	}

	private ProfileAggregate getProfileFromEvent(BaseEvent<?> event) {
		return profileAggregateESRepository.load(event.id.toString()).getWrappedAggregate().getAggregateRoot();
	}

	private ProfileEntity findExistingOrCreateQueryProfile(String id) {
		return profileRepository.findById(id).isPresent() ? profileRepository.findById(id).get() : new ProfileEntity();
	}

	private ProfileEntity buildQueryProfile(ProfileAggregate profileAggregate) {
		String METHOD = "buildQueryProfile() - ";
		logger.info(METHOD + "Entry -> userId:: " + profileAggregate.getUserId());

		ProfileEntity entity = findExistingOrCreateQueryProfile(profileAggregate.getUserId());
		if (entity.getUserId() != null) {
			logger.info(METHOD + "Update user:: " + entity.getUserId());
			ProfileEntity updateEntity = new ProfileEntity();
			updateEntity.setUserId(entity.getUserId());
			updateEntity.setAssociateId(entity.getAssociateId());
			updateEntity.setName(entity.getName());
			updateEntity.setEmail(entity.getEmail());
			updateEntity.setMobile(entity.getMobile());
			buildSkills(profileAggregate, updateEntity);
		} else {
			logger.info(METHOD + "Add user");
			entity.setUserId(profileAggregate.getUserId());
			entity.setAssociateId(profileAggregate.getAssociateId());
			entity.setName(profileAggregate.getName());
			entity.setEmail(profileAggregate.getEmail());
			entity.setMobile(profileAggregate.getMobile());
			buildSkills(profileAggregate, entity);
		}

		logger.info(METHOD + "Exit");
		return entity;
	}

	private ProfileEntity buildSkills(ProfileAggregate profileAggregate, ProfileEntity entity) {
		if (profileAggregate.getSkills() != null && !profileAggregate.getSkills().isEmpty()) {
			List<SkillEntity> skills = new ArrayList<>();
			for (Map.Entry<String, Integer> entry : profileAggregate.getSkills().entrySet()) {

				SkillLookupEntity lookupRow = cacheHelper.fetchSkillByName(entry.getKey());
				if (lookupRow != null) {
					SkillEntity skill = new SkillEntity();
					skill.setProfileEntity(entity);
					skill.setSkillId(lookupRow.getId());
					skill.setExpertiseLevel(entry.getValue());
					skills.add(skill);
				}
			}
			entity.setSkills(skills);
		}
		
		return entity;
	}

	private void persistProfile(ProfileEntity profileEntity) {
		profileRepository.save(profileEntity);
	}

}
