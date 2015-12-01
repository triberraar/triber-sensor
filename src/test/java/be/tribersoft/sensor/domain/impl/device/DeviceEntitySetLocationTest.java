package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceEntitySetLocationTest {

	private static final String LOCATION = "location";
	private static final String NAME = "name";

	private DeviceEntity device = new DeviceEntity(NAME);

	@Test
	public void setsLocationToNullWhenEmpty() {
		device.setLocation(Optional.ofNullable(null));

		assertThat(device.getLocation().isPresent()).isFalse();
	}

	@Test
	public void setsLocationWhenPresent() {
		device.setLocation(Optional.ofNullable(LOCATION));

		assertThat(device.getLocation().isPresent()).isTrue();
		assertThat(device.getLocation().get()).isEqualTo(LOCATION);
	}
}
