package be.tribersoft.common.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.semver.NotASemverException;

@RunWith(MockitoJUnitRunner.class)
public class NotASemverVersionExceptionHandlerProcessTest {
	private static final String ERROR_MESSAGE = "api.invalid.version";

	@InjectMocks
	private NotASemverExceptionHandler notASemverExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private NotASemverException notASemverException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE)).thenReturn(errorJson);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = notASemverExceptionHandler.process(notASemverException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}
}
