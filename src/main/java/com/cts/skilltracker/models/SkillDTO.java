package com.cts.skilltracker.models;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 
 */

public class SkillDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "The skill type is required. Valid values - TECHNICAL and NON_TECHNICAL.")
	private String skillType;
	
	@NotBlank(message = "The skill name is required.")
	private String skillName;
	
	@NotBlank(message = "The expertise level is required and value should be between 0 to 20.")
	@Min(0)
	@Max(20)
	private Integer expertiseLevel;

	public SkillDTO() {

	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getExpertiseLevel() {
		return expertiseLevel;
	}

	public void setExpertiseLevel(Integer expertiseLevel) {
		this.expertiseLevel = expertiseLevel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkillDTO [skillType=");
		builder.append(skillType);
		builder.append(", skillName=");
		builder.append(skillName);
		builder.append(", expertiseLevel=");
		builder.append(expertiseLevel);
		builder.append("]");
		return builder.toString();
	}

}
