package com.cts.skilltracker.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skill_lookup")
public class SkillLookupEntity {

	@Id
	@Column(name = "skill_id")
	private Integer id;
	
	@Column(name = "skill_type")
	private String type;
	
	@Column(name = "skill_name")
	private String name;

	public SkillLookupEntity() {

	}

	public SkillLookupEntity(Integer id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkillLookupEntity [id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
