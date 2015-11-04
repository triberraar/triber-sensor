package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceEntitySetDescriptionTest {

	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";

	private DeviceEntity device = new DeviceEntity(NAME);

	@Test
	public void setsDescriptionToNullWhenEmpty() {
		device.setDescription(Optional.ofNullable(null));

		assertThat(device.getDescription().isPresent()).isFalse();
	}

	@Test
	public void setsDescriptionWhenPresent() {
		device.setDescription(Optional.ofNullable(DESCRIPTION));

		assertThat(device.getDescription().isPresent()).isTrue();
		assertThat(device.getDescription().get()).isEqualTo(DESCRIPTION);
	}
}
