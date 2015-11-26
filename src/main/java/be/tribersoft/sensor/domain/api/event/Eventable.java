package be.tribersoft.sensor.domain.api.event;

import org.springframework.hateoas.Identifiable;

import be.tribersoft.sensor.domain.impl.event.EventVisitor;

public interface Eventable extends Identifiable<String> {
	void accept(EventVisitor eventVisitor, EventMode eventMode);
}
