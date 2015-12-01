package be.tribersoft.sensor.domain.api.unit.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class UnitNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "unit.validation.not.found";
	}

}
