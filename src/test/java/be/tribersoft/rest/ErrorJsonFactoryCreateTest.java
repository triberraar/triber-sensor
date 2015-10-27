package be.tribersoft.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import be.tribersoft.common.rest.ErrorJson;
import be.tribersoft.common.rest.ErrorJsonFactory;

@RunWith(MockitoJUnitRunner.class)
public class ErrorJsonFactoryCreateTest {

	private static final String TRANSLATED_UNKNOWN_ERROR = "translated unknown error";
	private static final String TRANSLATED_DEFAULT_MESSAGE = "translated default message";
	private static final String DEFAULT_MESSAGE = "default message";
	private static final String TRANSLATED_VALUE = "translatedValue";
	private static final String KEY = "key";
	private static final String UNKNOWN_ERROR = "validation.error.unknown";

	@InjectMocks
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private MessageSource msgSource;

	@Mock
	private FieldError fieldError;

	@Before
	public void setup() {
		when(msgSource.getMessage(eq(KEY), eq(null), eq(KEY), any(Locale.class))).thenReturn(TRANSLATED_VALUE);
		when(fieldError.getDefaultMessage()).thenReturn(DEFAULT_MESSAGE);
		when(msgSource.getMessage(eq(DEFAULT_MESSAGE), eq(null), eq(DEFAULT_MESSAGE), any(Locale.class))).thenReturn(TRANSLATED_DEFAULT_MESSAGE);
		when(msgSource.getMessage(eq(UNKNOWN_ERROR), eq(null), eq(UNKNOWN_ERROR), any(Locale.class))).thenReturn(TRANSLATED_UNKNOWN_ERROR);
	}

	@Test
	public void returnsTranslatedMessage_forKey() {
		ErrorJson errorJson = errorJsonFactory.create(KEY);

		assertThat(errorJson.getKey()).isEqualTo(KEY);
		assertThat(errorJson.getMessage()).isEqualTo(TRANSLATED_VALUE);
	}

	@Test
	public void returnsTranslatedMessage_forFieldError() {
		ErrorJson errorJson = errorJsonFactory.create(fieldError);

		assertThat(errorJson.getKey()).isEqualTo(DEFAULT_MESSAGE);
		assertThat(errorJson.getMessage()).isEqualTo(TRANSLATED_DEFAULT_MESSAGE);
	}

	@Test
	public void returnsDefaultMessage_forNullFieldError() {
		ErrorJson errorJson = errorJsonFactory.create((FieldError) null);

		assertThat(errorJson.getKey()).isEqualTo(UNKNOWN_ERROR);
		assertThat(errorJson.getMessage()).isEqualTo(TRANSLATED_UNKNOWN_ERROR);
	}
}
