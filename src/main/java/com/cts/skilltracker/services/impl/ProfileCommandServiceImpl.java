package com.cts.skilltracker.services.impl;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.commands.CreateProfileCommand;
import com.cts.skilltracker.helpers.RabbitMQSender;
import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
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

	@Async
	@Override
	public CompletableFuture<ProfileRsp> createUserProfile(ProfileCreateDTO profileCreateDTO) {
		
		String METHOD = "createUserProfile() - ";
		logger.info(METHOD + "Entry -> ProfileCreateDTO:: " + profileCreateDTO.toString());

		CompletableFuture<ProfileRsp> futureResult = null;

		try {

			futureResult = commandGateway.send(new CreateProfileCommand(profileCreateDTO.getUserId(),
					profileCreateDTO.getAssociateId(), profileCreateDTO.getName(), profileCreateDTO.getEmail(),
					profileCreateDTO.getMobile(), profileCreateDTO.getSkills()));

			if (futureResult.isDone()) {
				logger.info(METHOD + "Future response msg:: " + futureResult.get());
				//This should publish the message on Rabbit MQ
				rmqSender.send(futureResult.get());
			}

		} catch (Exception ex) {
			logger.error(METHOD + "Exception occurred:: " + ex.getMessage());
		}
		logger.info(METHOD + "Exit");
		return futureResult;
	}

}
