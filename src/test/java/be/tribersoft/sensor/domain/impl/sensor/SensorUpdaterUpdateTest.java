package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeRepositoryImpl;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class SensorUpdaterUpdateTest {

	private static final String NAME = "name";
	private static final String UNIT_ID = "unit id";
	private static final String TYPE_ID = "type id";
	private static final Optional<String> DESCRIPTION = Optional.of("description");

	@InjectMocks
	private SensorUpdater sensorUpdater;

	@Mock
	private TypeRepositoryImpl typeRepository;

	@Mock
	private UnitRepositoryImpl unitRepository;

	@Mock
	private TypeEntity type;

	@Mock
	private UnitEntity unit;

	@Mock
	private SensorEntity sensor;

	@Mock
	private SensorMessage sensorMessage;

	@Before
	public void setUp() {
		when(typeRepository.getById(TYPE_ID)).thenReturn(type);
		when(unitRepository.getById(UNIT_ID)).thenReturn(unit);
		when(sensorMessage.getDescription()).thenReturn(DESCRIPTION);
		when(sensorMessage.getName()).thenReturn(NAME);
		when(sensorMessage.getTypeId()).thenReturn(TYPE_ID);
		when(sensorMessage.getUnitId()).thenReturn(UNIT_ID);
	}

	@Test
	public void createsASensor() {
		sensorUpdater.update(sensor, sensorMessage);

		verify(sensor).setName(NAME);
		verify(sensor).setDescription(DESCRIPTION);
		verify(sensor).setType(type);
		verify(sensor).setUnit(unit);
	}
}
