package be.tribersoft.sensor.domain.api.unit.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UnitNotFoundExceptionGetMessageTest {

	@Test
	public void returnsMessage() {
		assertThat(new UnitNotFoundException().getMessage()).isEqualTo("unit.validation.not.found");
	}
}
