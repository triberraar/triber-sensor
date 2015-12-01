package be.tribersoft.sensor.domain.api.device.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DeviceNotFoundExceptionGetMesageTest {

	@Test
	public void returnsMessage() {
		assertThat(new DeviceNotFoundException().getMessage()).isEqualTo("device.validation.not.found");
	}

}
