package com.cts.skilltracker.commands;

import java.util.Map;

public class UpdateProfileCommand extends BaseCommand<String> {

	public final Map<String,Integer> skills;

	public UpdateProfileCommand(String id, Map<String,Integer> skills) {
		super(id);
		this.skills = skills;
	}

}
