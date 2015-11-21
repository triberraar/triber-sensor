package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.tribersoft.sensor.domain.api.event.EventSubject;

public class DeviceEntityGetEventSubjectTest {

	@Test
	public void returnsDevice() {
		assertThat(new DeviceEntity().getEventSubject()).isEqualTo(EventSubject.DEVICE);
	}

}
