package be.tribersoft.sensor.rest.sensorReading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.sensorReading.SensorReadingService;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingResourceSaveTest {
	private static final String DEVICE_ID = "device id";
	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private SensorReadingResource sensorReadingResource;
	@Mock
	private SensorReadingService sensorReadingService;
	@Mock
	private SensorReadingPostJson sensorReadingPostJson;
	@Mock
	private SensorValidator sensorValidator;

	@Test
	public void delegatesToService() {
		sensorReadingResource.save(DEVICE_ID, SENSOR_ID, sensorReadingPostJson);

		verify(sensorReadingService).save(SENSOR_ID, sensorReadingPostJson);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
	}
}
