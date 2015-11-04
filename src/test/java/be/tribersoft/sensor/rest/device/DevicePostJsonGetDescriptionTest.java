package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class DevicePostJsonGetDescriptionTest {

	private static final String DESCRIPTION_VALUE = "description value";
	private DevicePostJson devicePostJson = new DevicePostJson();

	@Test
	public void returnsEmptyWhenDescriptionIsNull() {
		Whitebox.setInternalState(devicePostJson, "description", null);

		assertThat(devicePostJson.getDescription().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenDescriptionIsNotEmpty() {
		Whitebox.setInternalState(devicePostJson, "description", Optional.of(DESCRIPTION_VALUE));

		assertThat(devicePostJson.getDescription().get()).isEqualTo(DESCRIPTION_VALUE);

	}

}
