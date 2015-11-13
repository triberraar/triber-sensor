package be.tribersoft.sensor.domain.impl.sensorReading;

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

import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingRepositoryImplAllByDeviceTest {

	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private SensorReadingRepositoryImpl sensorReadingRepositoryImpl;

	@Mock
	private SensorReadingJpaRepository sensorReadingJpaRepository;
	@Mock
	private SensorReadingEntity sensorReadingEntity1, sensorReadingEntity2;

	@Before
	public void setUp() {
		when(sensorReadingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(SENSOR_ID)).thenReturn(Arrays.asList(sensorReadingEntity1, sensorReadingEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<? extends SensorReading> all = sensorReadingRepositoryImpl.allBySensor(SENSOR_ID);

		verify(sensorReadingJpaRepository).findAllBySensorIdOrderByCreationDateDesc(SENSOR_ID);
		assertThat(all).isEqualTo(Arrays.asList(sensorReadingEntity1, sensorReadingEntity2));
	}

}
