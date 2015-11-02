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
public class SensorResourceDeleteTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorResource sensorResource;
	@Mock
	private SensorService sensorService;
	@Mock
	private SensorDeleteJson sensorDeleteJson;

	@Before
	public void setUp() {
		when(sensorDeleteJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		sensorResource.delete(ID, sensorDeleteJson);

		verify(sensorService).delete(ID, VERSION);
	}
}
