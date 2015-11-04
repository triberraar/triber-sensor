package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class DevicePostJsonGetLocationTest {

	private static final String LOCATION_VALUE = "location value";
	private DevicePostJson devicePostJson = new DevicePostJson();

	@Test
	public void returnsEmptyWhenLocationIsNull() {
		Whitebox.setInternalState(devicePostJson, "location", null);

		assertThat(devicePostJson.getLocation().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenLocationIsNotEmpty() {
		Whitebox.setInternalState(devicePostJson, "location", Optional.of(LOCATION_VALUE));

		assertThat(devicePostJson.getLocation().get()).isEqualTo(LOCATION_VALUE);

	}

}
