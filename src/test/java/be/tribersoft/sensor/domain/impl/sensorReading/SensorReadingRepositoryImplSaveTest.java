package be.tribersoft.sensor.domain.impl.sensorReading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingRepositoryImplSaveTest {

	@InjectMocks
	private SensorReadingRepositoryImpl sensorReadingRepositoryImpl;

	@Mock
	private SensorReadingJpaRepository sensorReadingJpaRepository;
	@Mock
	private SensorReadingEntity sensorReadingEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		sensorReadingRepositoryImpl.save(sensorReadingEntity);

		verify(sensorReadingJpaRepository).save(sensorReadingEntity);
	}

}
