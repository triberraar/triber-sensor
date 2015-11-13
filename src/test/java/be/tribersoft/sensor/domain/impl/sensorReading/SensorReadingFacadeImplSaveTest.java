package be.tribersoft.sensor.domain.impl.sensorReading;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingFacadeImplSaveTest {

	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private SensorReadingFacadeImpl sensorReadingFacade;

	@Mock
	private SensorReadingFactory sensorReadingFactory;
	@Mock
	private SensorReadingRepositoryImpl sensorReadingRepositoryImpl;
	@Mock
	private SensorReadingMessage sensorReadingMessage;
	@Mock
	private SensorReadingEntity sensorReading;

	@Before
	public void setUp() {
		when(sensorReadingFactory.create(SENSOR_ID, sensorReadingMessage)).thenReturn(sensorReading);
	}

	@Test
	public void savesCreatedSensorReadings() {
		sensorReadingFacade.save(SENSOR_ID, sensorReadingMessage);

		verify(sensorReadingRepositoryImpl).save(sensorReading);
	}

}
