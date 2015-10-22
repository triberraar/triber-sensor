package be.tribersoft.rest;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
	@Inject
	@Qualifier("validationMessageSource")
	private MessageSource msgSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorJson processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();

		return processFieldError(error);
	}

	private ErrorJson processFieldError(FieldError error) {
		if (error != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String msg = msgSource.getMessage(error.getDefaultMessage(), null, error.getDefaultMessage(),
					currentLocale);
			return new ErrorJson(msg);
		}
		return null;
	}

}
