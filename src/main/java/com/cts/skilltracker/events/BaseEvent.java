package com.cts.skilltracker.events;


public class BaseEvent<T> {
	
	public final Long id;

    public BaseEvent(Long id) {
        this.id = id;
    }

}
