package com.cts.skilltracker.models;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */

public class ProfileUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<SkillDTO> skills;

	public ProfileUpdateDTO() {

	}

	public List<SkillDTO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}

}
