package be.tribersoft.common.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

@RunWith(MockitoJUnitRunner.class)
public class NotAnEventableExceptionHandlerProcessTest {
	private static final String ERROR_MESSAGE = "event.validation.not.an.eventable";

	@InjectMocks
	private NotAnEventableExceptionHandler notAnEventableExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private NotAnEventableException notAnEventableException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE)).thenReturn(errorJson);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = notAnEventableExceptionHandler.process(notAnEventableException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}
}
