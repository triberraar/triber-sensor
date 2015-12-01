package be.tribersoft.sensor.domain.api.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EventSubjectTest {

	@Test
	public void SENSORHasSensorAsMessage() {
		assertThat(EventSubject.SENSOR.getMessage()).isEqualTo("event.sensor");
	}

	@Test
	public void DEVICEHasDeviceAsMessage() {
		assertThat(EventSubject.DEVICE.getMessage()).isEqualTo("event.device");
	}

	@Test
	public void READINGHasReadingAsMessage() {
		assertThat(EventSubject.READING.getMessage()).isEqualTo("event.reading");
	}

}
