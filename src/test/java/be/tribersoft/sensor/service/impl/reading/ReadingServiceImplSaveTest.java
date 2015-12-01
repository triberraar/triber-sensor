package be.tribersoft.sensor.service.impl.reading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.reading.ReadingFacade;
import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.service.impl.reading.ReadingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReadingServiceImplSaveTest {

	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private ReadingServiceImpl readingService;
	@Mock
	private ReadingFacade readingFacade;
	@Mock
	private ReadingMessage readingMessage;

	@Test
	public void delegatesToFacade() {
		readingService.save(SENSOR_ID, readingMessage);

		verify(readingFacade).save(SENSOR_ID, readingMessage);
	}

}
