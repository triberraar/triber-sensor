package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

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
	private SensorEntity sensorEntity1, sensorEntity2;

	@Test
	public void delegatesToSpringDataRepository() {
		sensorRepositoryImpl.delete(sensorEntity1);

		verify(sensorJpaRepository).delete(sensorEntity1);
	}

	@Test
	public void delegatesToSpringDataREpositoryForList() {
		sensorRepositoryImpl.delete(Arrays.asList(sensorEntity1, sensorEntity2));

		verify(sensorJpaRepository).delete(sensorEntity1);
		verify(sensorJpaRepository).delete(sensorEntity2);
	}

}
