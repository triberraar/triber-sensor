package be.tribersoft.common.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IncorrectApiVersionExceptionHandlerProcessTest {
	private static final String CURRENT_API_VERSION = "current api version";
	private static final String API_VERSION = "api version";
	private static final String ERROR_MESSAGE = "api.incorrect.version";

	@InjectMocks
	private IncorrectApiVersionExceptionHandler incorrectApiExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private IncorrectApiVersionException incorrectApiVersionException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE, CURRENT_API_VERSION, API_VERSION)).thenReturn(errorJson);
		when(incorrectApiVersionException.getApiVersion()).thenReturn(API_VERSION);
		when(incorrectApiVersionException.getCurrentApiVersion()).thenReturn(CURRENT_API_VERSION);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = incorrectApiExceptionHandler.process(incorrectApiVersionException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}
}
