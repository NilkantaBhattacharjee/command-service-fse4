package com.cts.skilltracker.aggregates;

import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.cts.skilltracker.commands.CreateProfileCommand;
import com.cts.skilltracker.commands.UpdateProfileCommand;
import com.cts.skilltracker.events.ProfileCreatedEvent;
import com.cts.skilltracker.events.ProfileUpdateEvent;
import com.cts.skilltracker.models.SkillDTO;



@Aggregate
public class ProfileAggregate {

	@AggregateIdentifier
	private Long userId;

	private String associateId;
	private String name;
	private String email;
	private Integer mobile;
	private List<SkillDTO> skills;
	
	public ProfileAggregate() {
		
	}
	

	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
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



	public Integer getMobile() {
		return mobile;
	}



	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}



	public List<SkillDTO> getSkills() {
		return skills;
	}



	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}


	/*Create Event*/
	@CommandHandler
	public ProfileAggregate(CreateProfileCommand cmd) {
		AggregateLifecycle.apply(new ProfileCreatedEvent(cmd.id, cmd.associateId, cmd.name, cmd.email, cmd.mobile, cmd.skills));
	}

	@EventSourcingHandler
	protected void on(ProfileCreatedEvent profileCreatedEvent) {
		this.userId = profileCreatedEvent.id;
		this.associateId = profileCreatedEvent.associateId;
		this.name = profileCreatedEvent.name;
		this.email = profileCreatedEvent.email;
		this.mobile = profileCreatedEvent.mobile;
		this.skills = profileCreatedEvent.skills;

		AggregateLifecycle.apply(new ProfileCreatedEvent(this.userId, this.associateId, this.name, this.email, this.mobile, this.skills));
	}
	
	/*Update Event*/
	
	@CommandHandler
	public ProfileAggregate(UpdateProfileCommand cmd) {
		AggregateLifecycle.apply(new ProfileUpdateEvent(cmd.id, cmd.skills));
	}
	
	@EventSourcingHandler
	protected void on(ProfileUpdateEvent profileUpdateEvent) {
		this.userId = profileUpdateEvent.id;
		this.skills = profileUpdateEvent.skills;

		AggregateLifecycle.apply(new ProfileUpdateEvent(this.userId, this.skills));
	}

}
