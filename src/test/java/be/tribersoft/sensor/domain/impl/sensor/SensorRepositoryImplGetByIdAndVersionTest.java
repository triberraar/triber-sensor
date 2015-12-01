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

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.sensor.exception.SensorNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplGetByIdAndVersionTest {

	private static final String ID = "id";
	private static final long VERSION = 3L;
	private static final long DIFFERENT_VERSION = 4L;
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

		sensorRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test(expected = ConcurrentModificationException.class)
	public void failsWhenSensorHasDifferentVersion() {
		sensorRepositoryImpl.getByIdAndVersion(ID, DIFFERENT_VERSION);
	}

	@Test
	public void returnsSensor() {
		assertThat(sensorRepositoryImpl.getByIdAndVersion(ID, VERSION)).isSameAs(sensorEntity);
	}
}
