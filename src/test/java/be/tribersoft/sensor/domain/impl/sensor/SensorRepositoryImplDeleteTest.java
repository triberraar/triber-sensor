package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplDeleteTest {

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		sensorRepositoryImpl.delete(sensorEntity);

		verify(sensorJpaRepository).delete(sensorEntity);
	}

}
