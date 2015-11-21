package be.tribersoft.sensor.domain.api.event;

import org.springframework.hateoas.Identifiable;

public interface Eventable extends Identifiable<String> {

	EventSubject getEventSubject();
}
