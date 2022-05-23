package com.cts.skilltracker.events;

import java.util.Map;

public class ProfileUpdateEvent extends BaseEvent<String> {

	public final Map<String,Integer> skills;

	public ProfileUpdateEvent(String id, Map<String,Integer> skills) {
		super(id);
		this.skills = skills;
	}

}
