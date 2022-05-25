package com.cts.skilltracker.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.cts.skilltracker.utils.CommandSideConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "associate_profile")
public class ProfileEntity {

	public ProfileEntity() {

	}

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "associate_id")
	private String associateId;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "created_by")
	private String createdBy = CommandSideConstants.CREATED_MODIFIED_BY;

	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp createdOn;

	@OneToMany(mappedBy = "profileEntity", targetEntity = SkillEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<SkillEntity> skills = new ArrayList<>();

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public List<SkillEntity> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillEntity> skills) {
		this.skills.addAll(skills);
		for(SkillEntity skill: skills) {
			skill.setProfileEntity(this);
		}
	}

	public void addSkill(SkillEntity skill) {
		this.skills.add(skill);
		skill.setProfileEntity(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileEntity [userId=");
		builder.append(userId);
		builder.append(", associateId=");
		builder.append(associateId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", skills=");
		builder.append(skills);
		builder.append("]");
		return builder.toString();
	}

	

}
