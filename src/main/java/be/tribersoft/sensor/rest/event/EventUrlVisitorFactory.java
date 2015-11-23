package be.tribersoft.sensor.rest.event;

import javax.inject.Named;

@Named
public class EventUrlVisitorFactory {

	public EventUrlVisitor create() {
		return new EventUrlVisitor();
	}
}
