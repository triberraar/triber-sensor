package be.tribersoft.common.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.exception.StillInUseException;

@RunWith(MockitoJUnitRunner.class)
public class StillInUseExceptionHandlerProcessTest {
	private static final String ERROR_MESSAGE = "error message";

	@InjectMocks
	private StillInUseExceptionHandler stillInUseExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private StillInUseException stillInUseException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE)).thenReturn(errorJson);
		when(stillInUseException.getMessage()).thenReturn(ERROR_MESSAGE);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = stillInUseExceptionHandler.process(stillInUseException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}

}
