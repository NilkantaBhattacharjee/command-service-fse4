package com.cts.skilltracker.controllers;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
import com.cts.skilltracker.services.iface.ProfileCommandService;
import com.cts.skilltracker.utils.SkillTrackerConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = SkillTrackerConstants.PROFILE_ROUTE)
public class ProfileController {

	private static Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileCommandService profileCmdSvc;

	@Operation(summary = "Add profiles", description = "Add Profiles API", tags = { "profile-controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful creation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileRsp.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = SkillTrackerConstants.ADD_PROFILE_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProfileRsp> createProfile(
			@Parameter(description = SkillTrackerConstants.ADD_PROFILE_DESC, required = true, schema = @Schema(implementation = ProfileCreateDTO.class)) @Valid @RequestBody ProfileCreateDTO profileCreateDTO) {

		String METHOD = "createProfile() - ";
		logger.info(METHOD + "Entry -> ProfileCreateDTO:: " + profileCreateDTO.toString());
		ProfileRsp responseDTO = new ProfileRsp();
		CompletableFuture<ProfileRsp> futureResult = null;
		try {
			
			//Add validations here

			futureResult = profileCmdSvc.createUserProfile(profileCreateDTO);
			// Block and get the result of the future.
			responseDTO = futureResult.get();

		} catch (Exception ex) {
			logger.error(METHOD + "Exception occurred:: " + ex.getMessage());
		}

		logger.info(METHOD + "Exit");
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

	}

}
