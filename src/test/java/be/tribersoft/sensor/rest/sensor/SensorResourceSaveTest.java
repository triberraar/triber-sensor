package be.tribersoft.sensor.rest.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class SensorResourceSaveTest {
	private static final String API_VERSION = "apiVersion";
	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorResource sensorDeviceResource;
	@Mock
	private SensorService sensorService;
	@Mock
	private SensorPostJson sensorPostJson;
	@Mock
	private VersionValidator versionValidator;

	@Test
	public void delegatesToService() {
		sensorDeviceResource.save(API_VERSION, DEVICE_ID, sensorPostJson);

		verify(sensorService).save(DEVICE_ID, sensorPostJson);
		verify(versionValidator).validate(API_VERSION);
	}
}
