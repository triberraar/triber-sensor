package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;

@RunWith(MockitoJUnitRunner.class)
public class SensorEntityConstructorTest {
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String NAME = "name";

	@Mock
	private TypeEntity type;
	@Mock
	private UnitEntity unit;
	@Mock
	private DeviceEntity device;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
	}

	@Test
	public void constructsSuccessfully() {
		SensorEntity sensor = new SensorEntity(NAME, device, type, unit);

		assertThat(sensor.getName()).isEqualTo(NAME);
		assertThat(sensor.getType()).isSameAs(type);
		assertThat(sensor.getUnit()).isSameAs(unit);
		assertThat(sensor.getDevice()).isSameAs(device);
		assertThat(sensor.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}

}
