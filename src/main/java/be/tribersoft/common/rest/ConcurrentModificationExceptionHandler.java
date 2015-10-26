package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;

@ControllerAdvice
public class ConcurrentModificationExceptionHandler {
	private static final String ERROR_MESSAGE = "validation.concurrent.modification";

	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(ConcurrentModificationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorJson process(ConcurrentModificationException ex) {
		return errorJsonFactory.create(ERROR_MESSAGE);
	}

}
