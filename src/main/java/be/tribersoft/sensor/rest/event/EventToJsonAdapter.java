package be.tribersoft.sensor.rest.event;

import java.util.Date;

import be.tribersoft.sensor.domain.api.event.Event;

public class EventToJsonAdapter {

	public static final String EVENTS = "events";

	private Event event;

	public EventToJsonAdapter(Event event) {
		this.event = event;
	}

	public String getEventId() {
		return event.getEventId();
	}

	public Date getCreationDate() {
		return event.getCreationDate();
	}

	public String getEventSubject() {
		return event.getEventSubject().getMessage();
	}

	public String getEventMode() {
		return event.getEventMode().getMessage();
	}
}
