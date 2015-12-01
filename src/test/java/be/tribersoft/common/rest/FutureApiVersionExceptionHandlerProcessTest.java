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
public class FutureApiVersionExceptionHandlerProcessTest {
	private static final String CURRENT_API_VERSION = "current api version";
	private static final String API_VERSION = "api version";
	private static final String ERROR_MESSAGE = "api.future.version";

	@InjectMocks
	private FutureApiVersionExceptionHandler futureApiVersionExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private FutureApiVersionException futureApiVersionException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE, CURRENT_API_VERSION, API_VERSION)).thenReturn(errorJson);
		when(futureApiVersionException.getApiVersion()).thenReturn(API_VERSION);
		when(futureApiVersionException.getCurrentApiVersion()).thenReturn(CURRENT_API_VERSION);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = futureApiVersionExceptionHandler.process(futureApiVersionException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}
}
