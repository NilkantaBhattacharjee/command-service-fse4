package com.cts.skilltracker.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.commands.CreateProfileCommand;
import com.cts.skilltracker.entities.ProfileEntity;
import com.cts.skilltracker.entities.SkillEntity;
import com.cts.skilltracker.entities.SkillLookupEntity;
import com.cts.skilltracker.helpers.RabbitMQSender;
import com.cts.skilltracker.helpers.SkillLookupCacheHelper;
import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
import com.cts.skilltracker.models.ProfileUpdateDTO;
import com.cts.skilltracker.repositories.ProfileRepository;
import com.cts.skilltracker.services.iface.ProfileCommandService;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

	private static Logger logger = LoggerFactory.getLogger(ProfileCommandServiceImpl.class);

	@Autowired
	CommandGateway commandGateway;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	SkillLookupCacheHelper cacheHelper;

	@Autowired
	RabbitMQSender rmqSender;

	public ProfileCommandServiceImpl() {

	}

	@Override
	public ProfileRsp createUserProfile(ProfileCreateDTO profileCreateDTO) {

		String METHOD = "createUserProfile() - ";
		logger.info(METHOD + "Entry -> ProfileCreateDTO:: " + profileCreateDTO.toString());

		ProfileRsp profileRsp = null;
		String message = "";

		try {

			logger.info(METHOD + "Sending create command using Axon command gateway...");
			message = commandGateway.sendAndWait(new CreateProfileCommand(UUID.randomUUID().toString(),
					profileCreateDTO.getAssociateId(), profileCreateDTO.getName(), profileCreateDTO.getEmail(),
					profileCreateDTO.getMobile(), profileCreateDTO.getSkills()));

			logger.info(METHOD + "Message received from command gateway after profile creation:: " + message);

			if (message != null && !message.equalsIgnoreCase("")) {
				profileRsp = new ProfileRsp();
				profileRsp.setUserId(message);
				profileRsp.setName(profileCreateDTO.getName());
				profileRsp.setAssociateId(profileCreateDTO.getAssociateId());
				profileRsp.setEmail(profileCreateDTO.getEmail());
				profileRsp.setMobile(profileCreateDTO.getMobile());
				profileRsp.setSkills(profileCreateDTO.getSkills());
				// This should publish the message on Rabbit MQ
				logger.info(METHOD + "Publishing message on Rabbit MQ...");
				rmqSender.send(profileRsp);
			}

		} catch (Exception ex) {
			logger.error(METHOD + "Exception occurred:: " + ex.getMessage());
		}
		logger.info(METHOD + "Exit");
		return profileRsp;
	}

	@Override
	public ProfileRsp updateUserProfile(String userId, ProfileUpdateDTO profileUpdateDTO) {

		String METHOD = "updateUserProfile() - ";
		logger.info(METHOD + "Entry -> userId:: " + userId + "ProfileUpdateDTO:: " + profileUpdateDTO.toString());

		ProfileRsp profileRsp = null;

		try {

			// logger.info(METHOD + "Sending update command using Axon command gateway...");
			// message = commandGateway.sendAndWait(new UpdateProfileCommand(userId,
			// profileUpdateDTO.getSkills()));

			if (profileRepository.findById(userId).isPresent()) {
				logger.info(METHOD + "Updating skills for profile:: " + userId);
				ProfileEntity profileEntity = profileRepository.findById(userId).get();
				profileEntity.getSkills().clear();
				profileEntity = buildSkills(profileUpdateDTO.getSkills(), profileEntity);
				// Save and fetch updated entity
				profileEntity = profileRepository.save(profileEntity);
				// Construct the profileRsp from updated profile entity
				profileRsp = new ProfileRsp();
				profileRsp.setUserId(profileEntity.getUserId());
				profileRsp.setAssociateId(profileEntity.getAssociateId());
				profileRsp.setName(profileEntity.getName());
				profileRsp.setEmail(profileEntity.getEmail());
				profileRsp.setMobile(profileEntity.getMobile());
				profileRsp.setSkills(profileUpdateDTO.getSkills());
				// This should publish the message on Rabbit MQ
				logger.info(METHOD + "Publishing updated profile data on Rabbit MQ...");
				rmqSender.send(profileRsp);

			}

		} catch (Exception ex) {
			logger.error(METHOD + "Exception occurred:: " + ex.getMessage());
		}

		logger.info(METHOD + "Exit");
		return profileRsp;
	}

	/**
	 * This method is used to build the skills for a profile
	 * 
	 * @param skillMap
	 * @param entity
	 * @return ProfileEntity
	 */
	private ProfileEntity buildSkills(Map<String, Integer> skillMap, ProfileEntity profileEntity) {
		List<SkillEntity> skills = profileEntity.getSkills();
		if (skillMap != null) {
			for (Map.Entry<String, Integer> entry : skillMap.entrySet()) {

				SkillLookupEntity lookupRow = cacheHelper.fetchSkillByName(entry.getKey());
				if (lookupRow != null) {
					SkillEntity skill = new SkillEntity();
					skill.setSkillId(lookupRow.getId());
					skill.setExpertiseLevel(entry.getValue());
					skills.add(skill);
				}
			}

			profileEntity.setSkills(skills);

		}

		return profileEntity;
	}

}
