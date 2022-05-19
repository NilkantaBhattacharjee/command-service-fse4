package com.cts.skilltracker.events;

import java.util.List;

import com.cts.skilltracker.models.SkillDTO;

public class ProfileUpdateEvent extends BaseEvent<Long> {

	public final List<SkillDTO> skills;

	public ProfileUpdateEvent(Long id, List<SkillDTO> skills) {
		super(id);
		this.skills = skills;
	}

}
