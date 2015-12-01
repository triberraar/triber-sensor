package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;

@RunWith(MockitoJUnitRunner.class)
public class SensorEntitySetDescriptionTest {

	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	@Mock
	private TypeEntity type;
	@Mock
	private UnitEntity unit;
	@Mock
	private DeviceEntity device;

	private SensorEntity sensor = new SensorEntity(NAME, device, type, unit);

	@Test
	public void setsDescriptionToNullWhenEmpty() {
		sensor.setDescription(Optional.ofNullable(null));

		assertThat(sensor.getDescription().isPresent()).isFalse();
	}

	@Test
	public void setsDescriptionWhenPresent() {
		sensor.setDescription(Optional.ofNullable(DESCRIPTION));

		assertThat(sensor.getDescription().isPresent()).isTrue();
		assertThat(sensor.getDescription().get()).isEqualTo(DESCRIPTION);
	}
}
