package com.cts.skilltracker.events;

import java.util.List;

import com.cts.skilltracker.models.SkillDTO;

public class ProfileCreatedEvent extends BaseEvent<Long> {

	public final String associateId;
	public final String name;
	public final String email;
	public final Integer mobile;
	public final List<SkillDTO> skills;

	public ProfileCreatedEvent(Long id, String associateId, String name, String email, Integer mobile,
			List<SkillDTO> skills) {
		super(id);
		this.associateId = associateId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.skills = skills;
	}

}
