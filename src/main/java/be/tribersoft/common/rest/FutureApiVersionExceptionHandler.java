package be.tribersoft.common.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FutureApiVersionExceptionHandler {
	private static final String ERROR_MESSAGE = "api.future.version";

	@Inject
	private ErrorJsonFactory errorJsonFactory;

	@ExceptionHandler(FutureApiVersionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorJson process(FutureApiVersionException ex) {
		return errorJsonFactory.create(ERROR_MESSAGE, ex.getCurrentApiVersion(), ex.getApiVersion());
	}

}
