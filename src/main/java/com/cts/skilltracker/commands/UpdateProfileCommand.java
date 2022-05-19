package com.cts.skilltracker.commands;

import java.util.List;

import com.cts.skilltracker.models.SkillDTO;

public class UpdateProfileCommand extends BaseCommand<Long> {

	public final List<SkillDTO> skills;

	public UpdateProfileCommand(Long id, List<SkillDTO> skills) {
		super(id);
		this.skills = skills;
	}

}
