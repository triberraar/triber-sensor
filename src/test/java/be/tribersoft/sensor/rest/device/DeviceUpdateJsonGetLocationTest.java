package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class DeviceUpdateJsonGetLocationTest {

	private static final String LOCATION_VALUE = "location value";
	private DeviceUpdateJson deviceUpdateJson = new DeviceUpdateJson();

	@Test
	public void returnsEmptyWhenLocationIsNull() {
		Whitebox.setInternalState(deviceUpdateJson, "location", null);

		assertThat(deviceUpdateJson.getLocation().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenLocationIsNotEmpty() {
		Whitebox.setInternalState(deviceUpdateJson, "location", Optional.of(LOCATION_VALUE));

		assertThat(deviceUpdateJson.getLocation().get()).isEqualTo(LOCATION_VALUE);

	}

}
