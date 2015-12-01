package be.tribersoft.sensor.domain.api.unit.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UnitStillInUseExceptionGetMessageTest {

	@Test
	public void returnsCorrectMessage() {
		assertThat(new UnitStillInUseException().getMessage()).isEqualTo("unit.validation.still.in.use");
	}
}
