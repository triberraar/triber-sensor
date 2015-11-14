package be.tribersoft.sensor.domain.impl.reading;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadingFacadeImplDeleteBySensorTest {

	private static final String SENSOR_ID = "sensor id";
	@InjectMocks
	private ReadingFacadeImpl readingFacadeImpl;
	@Mock
	private ReadingRepositoryImpl readingRepositoryImpl;
	@Mock
	private ReadingEntity reading1, reading2;

	@Before
	public void setUp() {
		when(readingRepositoryImpl.allBySensor(SENSOR_ID)).thenReturn(Arrays.asList(reading1, reading2));
	}

	@Test
	public void deletesAllReadingsConnectedToSensor() {
		readingFacadeImpl.deleteBySensor(SENSOR_ID);

		verify(readingRepositoryImpl).delete(Arrays.asList(reading1, reading2));
	}

}
