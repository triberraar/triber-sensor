package be.tribersoft.sensor.rest.sensor;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@Named
public class SensorValidator {

	@Inject
	private SensorRepository sensorRepository;

	public void validate(String deviceId, String id) {
		sensorRepository.getByDeviceIdAndId(deviceId, id);
	}

}
