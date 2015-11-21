package be.tribersoft.sensor.domain.impl.event;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.event.Eventable;

@Document(indexName = "triber-sensor", type = "event")
public class EventDocument {

	@Id
	private String id;
	@Field(type = FieldType.String)
	private EventMode eventMode;
	@Field(type = FieldType.String)
	private EventSubject eventSubject;
	@Field(type = FieldType.String)
	private String eventId;
	@Field(type = FieldType.Date)
	private Date creationDate;

	public EventDocument(Eventable eventable, EventMode eventMode) {
		this.eventMode = eventMode;
		this.eventSubject = eventable.getEventSubject();
		this.eventId = eventable.getId();
		id = UUID.randomUUID().toString();
		this.creationDate = DateFactory.generate();
	}

	public EventMode getEventMode() {
		return eventMode;
	}

	public EventSubject getEventSubject() {
		return eventSubject;
	}

	public String getEventId() {
		return eventId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getId() {
		return id;
	}

}
