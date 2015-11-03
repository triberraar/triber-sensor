package be.tribersoft.sensor.domain.impl.sensor;

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

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplAllTest {

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity1, sensorEntity2;

	@Before
	public void setUp() {
		when(sensorJpaRepository.findAllByOrderByCreationDateDesc()).thenReturn(Arrays.asList(sensorEntity1, sensorEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<SensorEntity> all = sensorRepositoryImpl.all();

		verify(sensorJpaRepository).findAllByOrderByCreationDateDesc();
		assertThat(all).isEqualTo(Arrays.asList(sensorEntity1, sensorEntity2));
	}

}
