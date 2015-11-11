package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IncorrectApiVersionExceptionHandler {
	private static final String ERROR_MESSAGE = "api.incorrect.version";

	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(IncorrectApiVersionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorJson process(IncorrectApiVersionException ex) {
		return errorJsonFactory.create(ERROR_MESSAGE, ex.getCurrentApiVersion(), ex.getApiVersion());
	}

}
