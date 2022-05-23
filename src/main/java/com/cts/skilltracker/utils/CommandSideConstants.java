package com.cts.skilltracker.utils;

public class CommandSideConstants {
	
	/*OpenAPI 3.0 constants*/
	
	public static final String OPEN_API_DEFAULT_PACKAGE = "com.cts.skilltracker.controllers";
	public static final String OPEN_API_GROUP_NAME = "Skill Tracker API";
	public static final String OPEN_API_TITLE = "Skill Tracker Restful API";
	public static final String OPEN_API_DESCRIPTION = "REST API Documentation for reference";
	public static final String OPEN_API_TC_URL = "http://springdoc.org";
	public static final String OPEN_API_LICENSE = "Apache 2.0";
	
	/*Endpoint definition*/
	public static final String PROFILE_ROUTE = "/engineer";
	public static final String ADD_PROFILE_ROUTE = "/add-profile";
	public static final String UPDATE_PROFILE_ROUTE = "/update-profile/{userId}";
	
	public static final String ADD_PROFILE_DESC = "This API is used to add Full Stack Engineer profile";
	public static final String UPDATE_PROFILE_DESC = "This API is used to update Full Stack Engineer skills";
	
	public static final String CREATED_MODIFIED_BY = "SYSTEM";
	
	/*Error Messages*/
	public static final String SKILL_NOT_FOUND_MSG = "Skill Not Found";
	public static final String INVALID_EXPERTISE_LEVEL_MSG = "Invalid expertise level";
	public static final String ADD_PROFILE_ERROR_MSG = "Add profile operation failed";
	public static final String INVALID_JSON_MSG = "Malformed JSON request";
	public static final String VALIDATION_ERROR_MSG = "Validation Errors";
	public static final String TYPE_MISMATCH_ERROR_MSG = "Type Mismatch";
	public static final String CONSTRAINT_VIOLATION_ERROR_MSG = "Constraint Violations";
	public static final String MISSING_PARAM_ERROR_MSG = "Missing Parameters";
	public static final String UNSUPPORTED_MEDIA_TYPE_ERROR_MSG = "Unsupported Media Type";
	public static final String METHOD_NOT_FOUND_ERROR_MSG = "Method Not Found";
	public static final String EXCEPTION_MSG = "Error occurred during request processing";

}
