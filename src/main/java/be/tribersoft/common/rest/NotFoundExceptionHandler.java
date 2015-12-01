package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

@ControllerAdvice
public class NotFoundExceptionHandler {
	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorJson process(NotFoundException ex) {
		return errorJsonFactory.create(ex.getMessage());
	}

}
