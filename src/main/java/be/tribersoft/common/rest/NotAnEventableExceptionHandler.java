package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

@ControllerAdvice
public class NotAnEventableExceptionHandler {
	private static final String ERROR_MESSAGE = "event.validation.not.an.eventable";

	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(NotAnEventableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorJson process(NotAnEventableException ex) {
		return errorJsonFactory.create(ERROR_MESSAGE);
	}

}
