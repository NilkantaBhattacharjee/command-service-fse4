package com.cts.skilltracker.services.impl;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.commands.CreateProfileCommand;
import com.cts.skilltracker.commands.UpdateProfileCommand;
import com.cts.skilltracker.helpers.RabbitMQSender;
import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
import com.cts.skilltracker.models.ProfileUpdateDTO;
import com.cts.skilltracker.services.iface.ProfileCommandService;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

	private static Logger logger = LoggerFactory.getLogger(ProfileCommandServiceImpl.class);

	@Autowired
	CommandGateway commandGateway;

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
			
			if(message!=null && !message.equalsIgnoreCase("")) {
				profileRsp = new ProfileRsp();
				profileRsp.setUserId(message);
				profileRsp.setName(profileCreateDTO.getName());
				profileRsp.setAssociateId(profileCreateDTO.getAssociateId());
				profileRsp.setEmail(profileCreateDTO.getEmail());
				profileRsp.setMobile(profileCreateDTO.getMobile());
				profileRsp.setSkills(profileCreateDTO.getSkills());
				//This should publish the message on Rabbit MQ
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
		String message = "";
		
		try {
			
			logger.info(METHOD + "Sending update command using Axon command gateway...");
			message = commandGateway.sendAndWait(new UpdateProfileCommand(userId,
					profileUpdateDTO.getSkills()));
			
			logger.info(METHOD + "Message received from command gateway after profile update:: " + message);
			
			if(message!=null && !message.equalsIgnoreCase("")) {
				profileRsp = new ProfileRsp();
				profileRsp.setUserId(message);
				profileRsp.setSkills(profileUpdateDTO.getSkills());
				//This should publish the message on Rabbit MQ
				logger.info(METHOD + "Publishing message on Rabbit MQ...");
				rmqSender.send(profileRsp);
			}
			
		} catch (Exception ex) {
			logger.error(METHOD + "Exception occurred:: " + ex.getMessage());
		}
		
		logger.info(METHOD + "Exit");
		return profileRsp;
	}

}
