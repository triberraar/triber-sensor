package be.tribersoft.sensor.rest.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.sensor.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class SensorResourceSaveTest {
	@InjectMocks
	private SensorResource sensorResource;
	@Mock
	private SensorService sensorService;
	@Mock
	private SensorPostJson sensorPostJson;

	@Test
	public void delegatesToService() {
		sensorResource.save(sensorPostJson);

		verify(sensorService).save(sensorPostJson);
	}
}
