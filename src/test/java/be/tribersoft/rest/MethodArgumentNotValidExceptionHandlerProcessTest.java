package be.tribersoft.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RunWith(MockitoJUnitRunner.class)
public class MethodArgumentNotValidExceptionHandlerProcessTest {

	@InjectMocks
	private MethodArgumentNotValidExceptionHandler handler;

	@Mock
	private MethodArgumentNotValidException methodArgumentNotValidException;

	@Mock
	private BindingResult bindingResult;

	@Mock
	private FieldError fieldError;

	@Mock
	private ErrorJsonFactory errorJsonFactory;

	@Mock
	private ErrorJson errorJson;

	@Before
	public void setUp() {
		when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldError()).thenReturn(fieldError);
		when(errorJsonFactory.create(fieldError)).thenReturn(errorJson);
	}

	@Test
	public void processExceptionToErrorJson() {
		ErrorJson processedErrorJson = handler.process(methodArgumentNotValidException);

		assertThat(processedErrorJson).isSameAs(errorJson);
	}
}
