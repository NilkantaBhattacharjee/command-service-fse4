package com.cts.skilltracker.handlers;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.aggregates.ProfileAggregate;
import com.cts.skilltracker.entities.ProfileEntity;
import com.cts.skilltracker.events.BaseEvent;
import com.cts.skilltracker.repositories.ProfileRepository;

@Component
public class ProfileEntityManager {
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
    @Qualifier("profileAggregateEventSourcingRepository")
	private EventSourcingRepository<ProfileAggregate> profileAggregateESRepository;
	
	@EventSourcingHandler
    void on(BaseEvent<?> event){
		persistProfile(buildQueryProfile(getProfileFromEvent(event)));
    }
	
	private ProfileAggregate getProfileFromEvent(BaseEvent<?> event){
        return profileAggregateESRepository.load(event.id.toString()).getWrappedAggregate().getAggregateRoot();
    }
	
	private ProfileEntity findExistingOrCreateQueryProfile(Long id){
        return profileRepository.findById(id).isPresent() ? profileRepository.findById(id).get() : new ProfileEntity();
    }

    private ProfileEntity buildQueryProfile(ProfileAggregate profileAggregate){
    	ProfileEntity entity = findExistingOrCreateQueryProfile(profileAggregate.getUserId());
        entity.setUserId(profileAggregate.getUserId());
        entity.setAssociateId(profileAggregate.getAssociateId());
        entity.setName(profileAggregate.getName());
        entity.setEmail(profileAggregate.getEmail());
        entity.setMobile(profileAggregate.getMobile());
        entity.setSkills(profileAggregate.getSkills());

        return entity;
    }
	
	private void persistProfile(ProfileEntity profileEntity){
		profileRepository.save(profileEntity);
    }

}
