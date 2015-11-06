package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceRepositoryImpl;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeRepositoryImpl;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class SensorFactoryCreateTest {

	private static final String NAME = "name";
	private static final String UNIT_ID = "unit id";
	private static final String TYPE_ID = "type id";
	private static final String DEVICE_ID = "device id";
	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final LocalDateTime DATE = LocalDateTime.now();

	@InjectMocks
	private SensorFactory sensorFactory;

	@Mock
	private TypeRepositoryImpl typeRepository;

	@Mock
	private UnitRepositoryImpl unitRepository;

	@Mock
	private DeviceRepositoryImpl deviceRepository;

	@Mock
	private TypeEntity type;

	@Mock
	private UnitEntity unit;

	@Mock
	private DeviceEntity device;

	@Mock
	private SensorMessage sensorMessage;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
		when(typeRepository.getById(TYPE_ID)).thenReturn(type);
		when(unitRepository.getById(UNIT_ID)).thenReturn(unit);
		when(deviceRepository.getById(DEVICE_ID)).thenReturn(device);
		when(sensorMessage.getDescription()).thenReturn(DESCRIPTION);
		when(sensorMessage.getName()).thenReturn(NAME);
		when(sensorMessage.getTypeId()).thenReturn(TYPE_ID);
		when(sensorMessage.getUnitId()).thenReturn(UNIT_ID);
	}

	@Test
	public void createsASensor() {
		SensorEntity createdSensor = sensorFactory.create(sensorMessage);

		assertThat(createdSensor.getName()).isEqualTo(NAME);
		assertThat(createdSensor.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(createdSensor.getType()).isEqualTo(type);
		assertThat(createdSensor.getUnit()).isEqualTo(unit);
		assertThat(createdSensor.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}
}
