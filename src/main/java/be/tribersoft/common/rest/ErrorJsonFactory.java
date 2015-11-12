package be.tribersoft.common.rest;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

@Named
public class ErrorJsonFactory {
	private static final String UNKNOWN_ERROR = "validation.error.unknown";

	@Inject
	@Qualifier("validationMessageSource")
	private MessageSource msgSource;

	public ErrorJson create(String key, String... parameters) {
		return new ErrorJson(key, getMessage(key, parameters));
	}

	private String getMessage(String message, String... parameters) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		return msgSource.getMessage(message, parameters, message, currentLocale);
	}

	public ErrorJson create(FieldError error) {
		if (error != null) {
			return new ErrorJson(error.getDefaultMessage(), getMessage(error.getDefaultMessage()));
		} else {
			return new ErrorJson(UNKNOWN_ERROR, getMessage(UNKNOWN_ERROR));
		}
	}
}
