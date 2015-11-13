package be.tribersoft.sensor.rest.reading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.reading.ReadingPostJson;
import be.tribersoft.sensor.rest.reading.ReadingResource;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RunWith(MockitoJUnitRunner.class)
public class ReadingResourceSaveTest {
	private static final String DEVICE_ID = "device id";
	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private ReadingResource readingResource;
	@Mock
	private ReadingService readingService;
	@Mock
	private ReadingPostJson readingPostJson;
	@Mock
	private SensorValidator sensorValidator;

	@Test
	public void delegatesToService() {
		readingResource.save(DEVICE_ID, SENSOR_ID, readingPostJson);

		verify(readingService).save(SENSOR_ID, readingPostJson);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
	}
}
