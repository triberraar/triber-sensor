package be.tribersoft.sensor.domain.api.type.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypeStillInUseExceptionGetMessageTest {

	@Test
	public void returnsRightMessage() {
		assertThat(new TypeStillInUseException().getMessage()).isEqualTo("type.validation.still.in.use");
	}
}
