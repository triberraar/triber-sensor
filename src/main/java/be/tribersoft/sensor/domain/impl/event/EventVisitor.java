package be.tribersoft.sensor.domain.impl.event;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.sensor.Sensor;

public interface EventVisitor {

	void visit(Reading reading, EventMode eventMode);

	void visit(Device device, EventMode eventMode);

	void visit(Sensor sensor, EventMode eventMode);

}
