package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingJpaRepository;
import be.tribersoft.sensor.domain.impl.reading.ReadingRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplAllByDeviceTest {

	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;

	@Mock
	private ReadingJpaRepository readingJpaRepository;
	@Mock
	private ReadingEntity readingEntity1, readingEntity2;

	@Before
	public void setUp() {
		when(readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(SENSOR_ID)).thenReturn(Arrays.asList(readingEntity1, readingEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<? extends Reading> all = readingRepositoryImpl.allBySensor(SENSOR_ID);

		verify(readingJpaRepository).findAllBySensorIdOrderByCreationDateDesc(SENSOR_ID);
		assertThat(all).isEqualTo(Arrays.asList(readingEntity1, readingEntity2));
	}

}
