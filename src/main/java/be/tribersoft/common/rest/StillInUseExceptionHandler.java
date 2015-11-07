package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.sensor.domain.api.exception.StillInUseException;

@ControllerAdvice
public class StillInUseExceptionHandler {
	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(StillInUseException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorJson process(StillInUseException ex) {
		return errorJsonFactory.create(ex.getMessage());
	}

}
