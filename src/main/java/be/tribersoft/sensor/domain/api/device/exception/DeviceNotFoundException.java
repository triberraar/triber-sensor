package be.tribersoft.sensor.domain.api.device.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class DeviceNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "device.validation.not.found";
	}

}
