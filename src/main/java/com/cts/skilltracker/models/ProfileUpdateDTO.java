package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 */

public class ProfileUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
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
