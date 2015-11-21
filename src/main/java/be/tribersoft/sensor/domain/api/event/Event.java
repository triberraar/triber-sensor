package be.tribersoft.sensor.domain.api.event;

import org.springframework.hateoas.Identifiable;

public interface Event extends Identifiable<String> {

	EventSubject getEventSubject();
}
