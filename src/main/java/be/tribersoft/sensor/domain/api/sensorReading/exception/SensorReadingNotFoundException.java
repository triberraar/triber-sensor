package be.tribersoft.sensor.domain.api.sensorReading.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class SensorReadingNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "sensor.reading.validation.not.found";
	}

}
