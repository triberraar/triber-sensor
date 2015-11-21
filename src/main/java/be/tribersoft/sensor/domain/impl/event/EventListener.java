package be.tribersoft.sensor.domain.impl.event;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import be.tribersoft.common.BeanInjector;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.Eventable;
import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

public class EventListener {

	private EventRepositoryImpl eventRepositoryImpl;
	private EventFactory eventFactory;

	public EventListener() {
	}

	@PostPersist
	public void postPersist(Object input) {
		handle(input, EventMode.CREATE);
	}

	@PostUpdate
	public void postUpdate(Object input) {
		handle(input, EventMode.UPDATE);
	}

	@PostRemove
	public void postRemove(Object input) {
		handle(input, EventMode.DELETE);
	}

	private void handle(Object input, EventMode eventMode) {
		BeanInjector.inject(this, this.eventRepositoryImpl, eventFactory);
		Eventable eventable = checkEventable(input);

		eventRepositoryImpl.save(eventFactory.create(eventable, eventMode));
	}

	private Eventable checkEventable(Object eventable) {
		if (!(eventable instanceof Eventable)) {
			throw new NotAnEventableException();
		}
		return (Eventable) eventable;
	}

}
