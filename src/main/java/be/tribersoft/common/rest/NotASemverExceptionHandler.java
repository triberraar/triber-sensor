package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import be.tribersoft.semver.NotASemverException;

@ControllerAdvice
public class NotASemverExceptionHandler {
	private static final String ERROR_MESSAGE = "api.invalid.version";

	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(NotASemverException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorJson process(NotASemverException ex) {
		return errorJsonFactory.create(ERROR_MESSAGE);
	}

}
