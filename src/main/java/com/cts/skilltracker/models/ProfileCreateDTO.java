package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 */

@Schema(name = "ProfileCreateDTO", description = "Request payload for adding a profile")
public class ProfileCreateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(name = "associateId", description = "Associate Id of profile", type = "String", minimum = "5", maximum = "30", required = true, example = "CTS001")
	@NotBlank(message = "The associate Id is required.")
	@Size(min = 5, max = 30, message = "The length of associate Id must be between 5 and 30 characters")
	@Pattern(regexp = "^([CTS]{3})[0-9]*$", message = "The associate id must start with the prefix CTS")
	private String associateId;
	
	@Schema(name = "name", description = "Name of profile", type = "String", minimum = "5", maximum = "30", required = true, example = "John Doe")
	@NotBlank(message = "The full name is required.")
	@Size(min = 5, max = 30, message = "The length of name must be between 5 and 30 characters")
	private String name;
	
	@Schema(name = "email", description = "Email adrdess of profile", type = "String", required = true, example = "john.doe@cognizant.com")
	@NotBlank(message = "The email is required")
	@Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
	private String email;
	
	@Schema(name = "mobile", description = "Mobile number of profile", type = "String", minimum = "10", maximum = "10", required = true, example = "1234567890")
	@NotBlank(message = "The mobile is required")
	@Pattern(regexp = "^\\d{10}$", message="Mobile Number should contain 10 digits")
	private String mobile;
	
	@Schema(name = "skills", description = "Technical and non-technical skills associated with profile", type = "object", required = true)
	private Map<String,Integer> skills;

	public ProfileCreateDTO() {

	}
	
	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String,Integer> getSkills() {
		return skills;
	}

	public void setSkills(Map<String,Integer> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileCreateDTO [associateId=");
		builder.append(associateId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", skills=");
		builder.append(skills);
		builder.append("]");
		return builder.toString();
	}

}
