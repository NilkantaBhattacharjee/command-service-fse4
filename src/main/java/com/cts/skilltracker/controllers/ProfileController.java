package com.cts.skilltracker.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.exceptions.AssociateExistsException;
import com.cts.skilltracker.exceptions.InvalidExpertiseLevelException;
import com.cts.skilltracker.exceptions.InvalidSkillException;
import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
import com.cts.skilltracker.models.ProfileUpdateDTO;
import com.cts.skilltracker.services.iface.ProfileCommandService;
import com.cts.skilltracker.utils.CommandSideConstants;
import com.cts.skilltracker.validators.ProfileValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = CommandSideConstants.PROFILE_ROUTE)
public class ProfileController {

	private static Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileCommandService profileCmdSvc;

	@Autowired
	ProfileValidator validator;

	@Operation(summary = "Add profile", description = CommandSideConstants.ADD_PROFILE_DESC, tags = {
			"profile-controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful creation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileRsp.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = CommandSideConstants.ADD_PROFILE_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileRsp> createProfile(
			@Parameter(description = "Request payload for adding a profile", required = true, schema = @Schema(implementation = ProfileCreateDTO.class)) @Valid @RequestBody ProfileCreateDTO profileCreateDTO) {

		String METHOD = "createProfile() - ";
		logger.info(METHOD + "Entry -> ProfileCreateDTO:: " + profileCreateDTO.toString());
		ProfileRsp responseDTO = new ProfileRsp();

		// Add validations here
		boolean existsFlag = validator.doesAssociateExist(profileCreateDTO.getAssociateId());
		if (existsFlag) {
			throw new AssociateExistsException(
					"Profile with Associate ID - " + profileCreateDTO.getAssociateId() + " already exists");
		}

		String skillNameDiff = validator.validateSkillName(profileCreateDTO.getSkills());

		if (skillNameDiff != null && !skillNameDiff.equalsIgnoreCase("")) {
			throw new InvalidSkillException("Invalid skills - " + skillNameDiff);
		}

		boolean flag = validator.validateLevel(profileCreateDTO.getSkills());
		if (flag) {
			throw new InvalidExpertiseLevelException("Invalid expertise level. Level should be between 0 and 20");
		}

		// Call service layer
		responseDTO = profileCmdSvc.createUserProfile(profileCreateDTO);

		logger.info(METHOD + "Exit");
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

	}

	@Operation(summary = "Update profile", description = CommandSideConstants.UPDATE_PROFILE_DESC, tags = {
			"profile-controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful creation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileRsp.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = CommandSideConstants.UPDATE_PROFILE_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileRsp> updateProfile(
			@Parameter(description = "Unique ID of the associate profile", required = true) @PathVariable(value = "userId") String userId,
			@Parameter(description = "Request payload for updating the skills associated with a profile", required = true, schema = @Schema(implementation = ProfileUpdateDTO.class)) @RequestBody ProfileUpdateDTO profileUpdateDTO) {

		String METHOD = "updateProfile() - ";
		logger.info(METHOD + "Entry -> Updating profile for user:: " + userId);
		ProfileRsp responseDTO = new ProfileRsp();

		// Add validations here

		String skillNameDiff = validator.validateSkillName(profileUpdateDTO.getSkills());

		if (skillNameDiff != null && !skillNameDiff.equalsIgnoreCase("")) {
			throw new InvalidSkillException("Invalid skills - " + skillNameDiff);
		}

		boolean flag = validator.validateLevel(profileUpdateDTO.getSkills());
		if (flag) {
			throw new InvalidExpertiseLevelException("Invalid expertise level. Level should be between 0 and 20");
		}
		
		//Need to add another validation during update

		// Call service layer
		responseDTO = profileCmdSvc.updateUserProfile(userId, profileUpdateDTO);

		logger.info(METHOD + "Exit");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

}
