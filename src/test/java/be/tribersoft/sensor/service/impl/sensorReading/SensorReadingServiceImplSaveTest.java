package be.tribersoft.sensor.service.impl.sensorReading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingFacade;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingServiceImplSaveTest {

	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private SensorReadingServiceImpl sensorReadingService;
	@Mock
	private SensorReadingFacade sensorReadingFacade;
	@Mock
	private SensorReadingMessage sensorReadingMessage;

	@Test
	public void delegatesToFacade() {
		sensorReadingService.save(SENSOR_ID, sensorReadingMessage);

		verify(sensorReadingFacade).save(SENSOR_ID, sensorReadingMessage);
	}

}
