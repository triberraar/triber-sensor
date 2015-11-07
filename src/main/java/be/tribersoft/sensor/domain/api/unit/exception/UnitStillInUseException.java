package be.tribersoft.sensor.domain.api.unit.exception;

import be.tribersoft.sensor.domain.api.exception.StillInUseException;

public class UnitStillInUseException extends StillInUseException {

	@Override
	public String getMessage() {
		return "unit.validation.still.in.use";
	}
}
