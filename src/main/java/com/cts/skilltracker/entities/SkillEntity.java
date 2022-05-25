package com.cts.skilltracker.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cts.skilltracker.utils.CommandSideConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "associate_skill_tracker")
public class SkillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tracker_id")
	private Integer trackerId;

	@Column(name = "skill_id")
	private Integer skillId;

	@Column(name = "expertise_level")
	private Integer expertiseLevel;

	@Column(name = "created_by")
	private String createdBy = CommandSideConstants.CREATED_MODIFIED_BY;

	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "last_modified_by")
	private String lastModifiedBy = CommandSideConstants.CREATED_MODIFIED_BY;

	@UpdateTimestamp
	@Column(name = "last_modified_on")
	private Timestamp lastModifiedOn;

	@ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@JsonIgnore
	private ProfileEntity profileEntity;

	public SkillEntity() {

	}

	public Integer getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(Integer trackerId) {
		this.trackerId = trackerId;
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public Integer getExpertiseLevel() {
		return expertiseLevel;
	}

	public void setExpertiseLevel(Integer expertiseLevel) {
		this.expertiseLevel = expertiseLevel;
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

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public ProfileEntity getProfileEntity() {
		return profileEntity;
	}

	public void setProfileEntity(ProfileEntity profileEntity) {
		this.profileEntity = profileEntity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkillEntity [trackerId=");
		builder.append(trackerId);
		builder.append(", skillId=");
		builder.append(skillId);
		builder.append(", expertiseLevel=");
		builder.append(expertiseLevel);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}

	

}
