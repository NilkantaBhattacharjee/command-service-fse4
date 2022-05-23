package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 */

@Schema(name = "ProfileRsp", description = "Response structure for Add/Update Profile API")
public class ProfileRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(name = "userId", description = "Unique Id of profile", type = "String", required = true, example = "578cb3d2-159e-4dba-9bcf-8d37943e4835")
	private String userId;
	
	@Schema(name = "associateId", description = "Associate Id of profile", type = "String", minimum = "5", maximum = "30", required = true, example = "CTS001")
	private String associateId;
	
	@Schema(name = "name", description = "Name of profile", type = "String", minimum = "5", maximum = "30", required = true, example = "John Doe")
	private String name;
	
	@Schema(name = "email", description = "Email adrdess of profile", type = "String", required = true, example = "john.doe@cognizant.com")
	private String email;
	
	@Schema(name = "mobile", description = "Mobile number of profile", type = "String", minimum = "10", maximum = "10", required = true, example = "1234567890")
	private String mobile;
	
	@Schema(name = "skills", description = "Technical and non-technical skills associated with profile", type = "object", required = true)
	private Map<String, Integer> skills;

	public ProfileRsp() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String, Integer> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, Integer> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileRsp [userId=");
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
