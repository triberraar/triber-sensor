package be.tribersoft.sensor.service.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplDeleteTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorServiceImpl sensorService;
	@Mock
	private SensorFacade sensorFacade;
	@Mock
	private ReadingService readingService;

	@Test
	public void deletesSensorAndConnectedReading() {
		sensorService.delete(ID, VERSION);

		verify(sensorFacade).delete(ID, VERSION);
		verify(readingService).deleteBySensor(ID);
	}

}
