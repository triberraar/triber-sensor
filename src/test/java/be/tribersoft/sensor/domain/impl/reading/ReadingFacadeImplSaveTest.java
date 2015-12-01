package be.tribersoft.sensor.domain.impl.reading;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingFacadeImpl;
import be.tribersoft.sensor.domain.impl.reading.ReadingFactory;
import be.tribersoft.sensor.domain.impl.reading.ReadingRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReadingFacadeImplSaveTest {

	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private ReadingFacadeImpl readingFacade;

	@Mock
	private ReadingFactory readingFactory;
	@Mock
	private ReadingRepositoryImpl readingRepositoryImpl;
	@Mock
	private ReadingMessage readingMessage;
	@Mock
	private ReadingEntity reading;

	@Before
	public void setUp() {
		when(readingFactory.create(SENSOR_ID, readingMessage)).thenReturn(reading);
	}

	@Test
	public void savesCreatedReadings() {
		readingFacade.save(SENSOR_ID, readingMessage);

		verify(readingRepositoryImpl).save(reading);
	}

}
