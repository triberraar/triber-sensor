package be.tribersoft.sensor.rest.event;

import java.util.Date;

import org.springframework.hateoas.core.Relation;

import be.tribersoft.sensor.domain.api.event.Event;

@Relation(collectionRelation = EventToJsonAdapter.EVENTS, value = EventToJsonAdapter.EVENT)
public class EventToJsonAdapter {

	public static final String EVENTS = "events";
	public static final String EVENT = "event";

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
