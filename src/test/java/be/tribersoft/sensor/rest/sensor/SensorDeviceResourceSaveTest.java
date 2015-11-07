package be.tribersoft.sensor.rest.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.sensor.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class SensorDeviceResourceSaveTest {
	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorDeviceResource sensorDeviceResource;
	@Mock
	private SensorService sensorService;
	@Mock
	private SensorPostJson sensorPostJson;

	@Test
	public void delegatesToService() {
		sensorDeviceResource.save(DEVICE_ID, sensorPostJson);

		verify(sensorService).save(DEVICE_ID, sensorPostJson);
	}
}
