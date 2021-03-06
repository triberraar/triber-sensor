package be.tribersoft.sensor.domain.impl.event;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.event.Eventable;

@Named
public class EventFactory {

	public EventDocument create(Eventable eventable, EventMode eventMode, EventSubject eventSubject) {
		return new EventDocument(eventable, eventMode, eventSubject);
	}
}
