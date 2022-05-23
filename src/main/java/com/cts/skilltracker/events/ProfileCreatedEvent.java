package com.cts.skilltracker.events;

import java.util.Map;

public class ProfileCreatedEvent extends BaseEvent<String> {

	public final String associateId;
	public final String name;
	public final String email;
	public final String mobile;
	public final Map<String,Integer> skills;

	public ProfileCreatedEvent(String id, String associateId, String name, String email, String mobile,
			Map<String,Integer> skills) {
		super(id);
		this.associateId = associateId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.skills = skills;
	}

}
