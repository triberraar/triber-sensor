package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.exception.SensorNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplGetByIdTest {

	private static final String ID = "id";
	private static final long VERSION = 3L;
	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;
	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity;

	@Before
	public void setUp() {
		when(sensorEntity.getVersion()).thenReturn(VERSION);
		when(sensorJpaRepository.findById(ID)).thenReturn(Optional.of(sensorEntity));
	}

	@Test(expected = SensorNotFoundException.class)
	public void failsWhenSensorDoesnExist() {
		when(sensorJpaRepository.findById(ID)).thenReturn(Optional.empty());

		sensorRepositoryImpl.getBydId(ID);
	}

	@Test
	public void returnsSensor() {
		assertThat(sensorRepositoryImpl.getBydId(ID)).isSameAs(sensorEntity);
	}
}
