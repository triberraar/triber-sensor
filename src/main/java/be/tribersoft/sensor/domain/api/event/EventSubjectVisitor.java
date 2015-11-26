package be.tribersoft.sensor.domain.api.event;

public interface EventSubjectVisitor {
	public void visitSensor(Event event);

	public void visitReading(Event event);

	public void visitDevice(Event event);
}
