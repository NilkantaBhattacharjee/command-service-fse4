package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */

public class ProfileRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String associateId;
	private String name;
	private String email;
	private Integer mobile;
	private List<SkillDTO> skills;

	public ProfileRsp() {

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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
