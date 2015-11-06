package be.tribersoft.sensor.domain.api.type.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypeNotFoundExceptionGetMessageTest {

	@Test
	public void returnsMessage() {
		assertThat(new TypeNotFoundException().getMessage()).isEqualTo("type.validation.not.found");
	}
}
