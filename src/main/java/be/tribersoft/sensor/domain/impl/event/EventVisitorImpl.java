package be.tribersoft.sensor.domain.impl.event;

import javax.inject.Inject;

import be.tribersoft.common.BeanInjector;
import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.sensor.Sensor;

public class EventVisitorImpl implements EventVisitor {
	@Inject
	private EventRepositoryImpl eventRepositoryImpl;
	@Inject
	private EventFactory eventFactory;

	@Override
	public void visit(Reading reading, EventMode eventMode) {
		BeanInjector.inject(this, this.eventRepositoryImpl);
		eventRepositoryImpl.save(eventFactory.create(reading, eventMode, EventSubject.READING));
	}

	@Override
	public void visit(Device device, EventMode eventMode) {
		BeanInjector.inject(this, this.eventRepositoryImpl);
		eventRepositoryImpl.save(eventFactory.create(device, eventMode, EventSubject.DEVICE));
	}

	@Override
	public void visit(Sensor sensor, EventMode eventMode) {
		BeanInjector.inject(this, this.eventRepositoryImpl);
		eventRepositoryImpl.save(eventFactory.create(sensor, eventMode, EventSubject.SENSOR));
	}

}
