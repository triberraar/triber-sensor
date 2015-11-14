package be.tribersoft.sensor.domain.api.reading.exception;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

public class ReadingNotFoundException extends NotFoundException {

	@Override
	public String getMessage() {
		return "reading.validation.not.found";
	}

}
