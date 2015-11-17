package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplCountBySensorTest {

	private static final String SENSOR_ID = "sensor id";
	private static final int COUNT = 23;

	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;

	@Mock
	private ReadingJpaRepository readingJpaRepository;

	@Before
	public void setUp() {
		when(readingJpaRepository.countBySensorId(SENSOR_ID)).thenReturn(COUNT);
	}

	@Test
	public void delegatesToJpaRepository() {
		int result = readingRepositoryImpl.countBySensor(SENSOR_ID);

		verify(readingJpaRepository).countBySensorId(SENSOR_ID);
		assertThat(result).isEqualTo(COUNT);
	}

}
