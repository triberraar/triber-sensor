package be.tribersoft.sensor.domain.api.sensor.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class SensorNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "sensor.validation.not.found";
	}

}
