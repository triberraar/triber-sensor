package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class DeviceUpdateJsonGetDescriptionTest {

	private static final String DESCRIPTION_VALUE = "description value";
	private DeviceUpdateJson deviceUpdateJson = new DeviceUpdateJson();

	@Test
	public void returnsEmptyWhenDescriptionIsNull() {
		Whitebox.setInternalState(deviceUpdateJson, "description", null);

		assertThat(deviceUpdateJson.getDescription().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenDescriptionIsNotEmpty() {
		Whitebox.setInternalState(deviceUpdateJson, "description", Optional.of(DESCRIPTION_VALUE));

		assertThat(deviceUpdateJson.getDescription().get()).isEqualTo(DESCRIPTION_VALUE);

	}

}
