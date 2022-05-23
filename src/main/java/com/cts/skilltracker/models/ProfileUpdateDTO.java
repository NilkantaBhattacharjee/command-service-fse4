package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 */

@Schema(name = "ProfileCreateDTO", description = "Request payload for updating a profile")
public class ProfileUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(name = "skills", description = "Technical and non-technical skills associated with profile", type = "object", required = true)
	private Map<String, Integer> skills;

	public ProfileUpdateDTO() {

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
		builder.append("ProfileUpdateDTO [skills=");
		builder.append(skills);
		builder.append("]");
		return builder.toString();
	}

}
