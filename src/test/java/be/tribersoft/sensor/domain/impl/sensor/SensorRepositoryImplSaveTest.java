package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplSaveTest {

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		sensorRepositoryImpl.save(sensorEntity);

		verify(sensorJpaRepository).save(sensorEntity);
	}

}
