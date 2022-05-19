package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

/**
 * 
 */
public class ProfileCreateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId;
	
	@NotBlank(message = "The associate Id is required.")
	@Size(min = 5, max = 30, message = "The length of associate Id must be between 5 and 30 characters.")
	private String associateId;
	
	@NotBlank(message = "The full name is required.")
	@Size(min = 5, max = 30, message = "The length of name must be between 5 and 30 characters.")
	private String name;
	
	@Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
	@NotBlank(message = "The email is required")
	private String email;
	
	@Digits(message="Mobile Number should contain 10 digits.", fraction = 0, integer = 10)
	private Integer mobile;
	
	private List<SkillDTO> skills;

	public ProfileCreateDTO() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public List<SkillDTO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileCreateDTO [userId=");
		builder.append(userId);
		builder.append(", associateId=");
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
