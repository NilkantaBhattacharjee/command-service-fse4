package com.cts.skilltracker.config;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cts.skilltracker.aggregates.ProfileAggregate;

@Configuration
public class AxonConfig {

	@Bean
	EventSourcingRepository<ProfileAggregate> profileAggregateEventSourcingRepository(EventStore eventStore) {
		EventSourcingRepository<ProfileAggregate> repository = EventSourcingRepository.builder(ProfileAggregate.class)
				.eventStore(eventStore).build();
		return repository;
	}

}
