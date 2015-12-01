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

import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorUpdaterUpdateTest {

	private static final String NAME = "name";
	private static final Optional<String> DESCRIPTION = Optional.of("description");

	@InjectMocks
	private SensorUpdater sensorUpdater;

	@Mock
	private SensorEntity sensor;

	@Mock
	private SensorUpdateMessage sensorUpdateMessage;

	@Before
	public void setUp() {
		when(sensorUpdateMessage.getDescription()).thenReturn(DESCRIPTION);
		when(sensorUpdateMessage.getName()).thenReturn(NAME);
	}

	@Test
	public void updatesASensor() {
		sensorUpdater.update(sensor, sensorUpdateMessage);

		verify(sensor).setName(NAME);
		verify(sensor).setDescription(DESCRIPTION);
	}
}
