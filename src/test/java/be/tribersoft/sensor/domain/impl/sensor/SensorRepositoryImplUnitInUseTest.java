package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplUnitInUseTest {

	private static final String UNIT_ID = "unit id";

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;

	@Test
	public void returnsFalseWhenNoSensorsAreUsingThisUnit() {
		when(sensorJpaRepository.countByUnitId(UNIT_ID)).thenReturn(0L);

		assertThat(sensorRepositoryImpl.unitInUse(UNIT_ID)).isFalse();
	}

	@Test
	public void returnsTrueWhenAtLeastASensorIsUsingThisUnit() {
		when(sensorJpaRepository.countByUnitId(UNIT_ID)).thenReturn(1L);

		assertThat(sensorRepositoryImpl.unitInUse(UNIT_ID)).isTrue();
	}

}
