package be.tribersoft.sensor.domain.api.sensor.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SensorNotFoundExceptionGetMessageTest {

	@Test
	public void returnsMessage() {
		assertThat(new SensorNotFoundException().getMessage()).isEqualTo("sensor.validation.not.found");
	}
}
