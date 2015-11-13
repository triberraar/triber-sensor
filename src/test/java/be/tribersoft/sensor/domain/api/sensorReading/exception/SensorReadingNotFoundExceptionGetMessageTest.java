package be.tribersoft.sensor.domain.api.sensorReading.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SensorReadingNotFoundExceptionGetMessageTest {

	@Test
	public void returnsMessage() {
		assertThat(new SensorReadingNotFoundException().getMessage()).isEqualTo("sensor.reading.validation.not.found");
	}
}
