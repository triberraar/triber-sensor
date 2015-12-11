package be.tribersoft.common.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class NotFoundExceptionHandlerProcessTest {

	private static final String MESSAGE = "message";

	@InjectMocks
	private NotFoundExceptionHandler exceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private NotFoundException notFoundException;

	@Mock
	private ErrorJson errorJson;

	@Before
	public void setUp() {
		when(notFoundException.getMessage()).thenReturn(MESSAGE);
		when(errorJsonFactory.create(MESSAGE)).thenReturn(errorJson);
	}

	@Test
	public void createsAnError() {
		ErrorJson resultingErrorJson = exceptionHandler.process(notFoundException);

		assertThat(resultingErrorJson).isSameAs(errorJson);
	}
}
