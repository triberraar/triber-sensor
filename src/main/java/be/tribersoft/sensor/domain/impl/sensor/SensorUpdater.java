package be.tribersoft.sensor.domain.impl.sensor;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

@Named
public class SensorUpdater {

	public void update(SensorEntity sensor, SensorUpdateMessage sensorUpdateMessage) {
		sensor.setName(sensorUpdateMessage.getName());
		sensor.setDescription(sensorUpdateMessage.getDescription());
	}
}
