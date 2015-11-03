package be.tribersoft.sensor.rest.sensor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.sensor.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class SensorResourceUpdateTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorResource sensorResource;
	@Mock
	private SensorService sensorService;
	@Mock
	private SensorUpdateJson sensorUpdateJson;

	@Before
	public void setUp() {
		when(sensorUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		sensorResource.update(ID, sensorUpdateJson);

		verify(sensorService).update(ID, VERSION, sensorUpdateJson);
	}
}
