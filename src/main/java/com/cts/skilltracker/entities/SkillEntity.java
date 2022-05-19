package com.cts.skilltracker.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "associate_skill_tracker")
public class SkillEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tracker_id")
	private String trackerId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "skill_id")
	private Integer skillId;
	
	@Column(name = "expertise_level")
	private Integer expertiseLevel;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "last_modified_by")
	private String lastModifiedBy;
	
	@CreationTimestamp
	@Column(name = "last_modified_on")
	private Timestamp lastModifiedOn;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private ProfileEntity profileEntity;

}
