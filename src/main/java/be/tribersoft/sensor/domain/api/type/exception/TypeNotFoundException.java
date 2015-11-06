package be.tribersoft.sensor.domain.api.type.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class TypeNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "type.validation.not.found";
	}

}
