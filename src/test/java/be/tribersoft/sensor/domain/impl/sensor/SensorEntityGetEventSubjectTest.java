package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.tribersoft.sensor.domain.api.event.EventSubject;

public class SensorEntityGetEventSubjectTest {

	@Test
	public void returnsSensor() {
		assertThat(new SensorEntity().getEventSubject()).isEqualTo(EventSubject.SENSOR);
	}

}
