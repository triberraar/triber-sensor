package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplTypeInUseTest {

	private static final String TYPE_ID = "type id";

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;

	@Test
	public void returnsFalseWhenNoSensorsAreUsingThisType() {
		when(sensorJpaRepository.countByTypeId(TYPE_ID)).thenReturn(0L);

		assertThat(sensorRepositoryImpl.typeInUse(TYPE_ID)).isFalse();
	}

	@Test
	public void returnsTrueWhenAtLeastASensorIsUsingThisType() {
		when(sensorJpaRepository.countByTypeId(TYPE_ID)).thenReturn(1L);

		assertThat(sensorRepositoryImpl.typeInUse(TYPE_ID)).isTrue();
	}

}
