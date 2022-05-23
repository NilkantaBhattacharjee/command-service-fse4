package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

/**
 * 
 */
public class ProfileCreateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "The associate Id is required.")
	@Size(min = 5, max = 30, message = "The length of associate Id must be between 5 and 30 characters")
	@Pattern(regexp = "^CTS", message = "The associate id must start with the prefix CTS")
	private String associateId;
	
	@NotBlank(message = "The full name is required.")
	@Size(min = 5, max = 30, message = "The length of name must be between 5 and 30 characters")
	private String name;
	
	@NotBlank(message = "The email is required")
	@Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
	private String email;
	
	@NotBlank(message = "The mobile is required")
	@Pattern(regexp = "^\\d{10}$", message="Mobile Number should contain 10 digits")
	private String mobile;
	
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
