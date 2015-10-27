package be.tribersoft.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.rest.ConcurrentModificationExceptionHandler;
import be.tribersoft.common.rest.ErrorJson;
import be.tribersoft.common.rest.ErrorJsonFactory;
import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentModificationExceptionHandlerProcessTest {

	private static final String ERROR_MESSAGE = "validation.concurrent.modification";

	@InjectMocks
	private ConcurrentModificationExceptionHandler concurrentModificationExceptionHandler;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Mock
	private ConcurrentModificationException concurrentModificationException;

	@Before
	public void setUp() {
		when(errorJsonFactory.create(ERROR_MESSAGE)).thenReturn(errorJson);
	}

	@Test
	public void returnsAnErrorJson() {
		ErrorJson processedErrorJson = concurrentModificationExceptionHandler.process(concurrentModificationException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}

}
