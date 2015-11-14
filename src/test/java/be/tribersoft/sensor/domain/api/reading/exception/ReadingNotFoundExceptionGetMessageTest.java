package be.tribersoft.sensor.domain.api.reading.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.tribersoft.sensor.domain.api.reading.exception.ReadingNotFoundException;

public class ReadingNotFoundExceptionGetMessageTest {

	@Test
	public void returnsMessage() {
		assertThat(new ReadingNotFoundException().getMessage()).isEqualTo("reading.validation.not.found");
	}
}
