package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.tribersoft.sensor.domain.api.event.EventSubject;

public class ReadingEntityGetEventSubjectTest {

	@Test
	public void returnsReading() {
		assertThat(new ReadingEntity().getEventSubject()).isEqualTo(EventSubject.READING);
	}

}
