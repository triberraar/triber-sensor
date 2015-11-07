package be.tribersoft.sensor.domain.api.type.exception;

import be.tribersoft.sensor.domain.api.exception.StillInUseException;

public class TypeStillInUseException extends StillInUseException {

	@Override
	public String getMessage() {
		return "type.validation.still.in.use";
	}
}
