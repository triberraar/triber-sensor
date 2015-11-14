package be.tribersoft.sensor.service.impl.reading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.reading.ReadingFacade;

@RunWith(MockitoJUnitRunner.class)
public class ReadingServiceImplDeleteBySensorTest {

	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private ReadingServiceImpl readingServiceImpl;
	@Mock
	private ReadingFacade readingFacade;

	@Test
	public void delegatesToFacade() {
		readingServiceImpl.deleteBySensor(SENSOR_ID);

		verify(readingFacade).deleteBySensor(SENSOR_ID);
	}
}
