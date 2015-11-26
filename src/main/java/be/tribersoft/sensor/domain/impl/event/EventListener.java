package be.tribersoft.sensor.domain.impl.event;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.Eventable;
import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

public class EventListener {

	public EventListener() {
	}

	@PostPersist
	public void postPersist(Object input) {
		handle(input, EventMode.CREATED);
	}

	@PostUpdate
	public void postUpdate(Object input) {
		handle(input, EventMode.UPDATED);
	}

	@PostRemove
	public void postRemove(Object input) {
		handle(input, EventMode.DELETED);
	}

	private void handle(Object input, EventMode eventMode) {
		Eventable eventable = checkEventable(input);
		eventable.accept(new EventVisitorImpl(), eventMode);
	}

	private Eventable checkEventable(Object eventable) {
		if (!(eventable instanceof Eventable)) {
			throw new NotAnEventableException();
		}
		return (Eventable) eventable;
	}

}
