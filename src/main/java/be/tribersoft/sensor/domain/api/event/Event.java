package be.tribersoft.sensor.domain.api.event;

import java.util.Date;

public interface Event {

	EventMode getEventMode();

	EventSubject getEventSubject();

	String getEventId();

	Date getCreationDate();

}
