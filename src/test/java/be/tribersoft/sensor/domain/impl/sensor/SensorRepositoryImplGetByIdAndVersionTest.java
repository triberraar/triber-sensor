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

	private static final long DIFFERENT_VERSION = 3L;
	private static final long VERSION = 2l;
	private static final String ID = "id";

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity;

	@Before
	public void setUp() {
		when(sensorJpaRepository.findById(ID)).thenReturn(Optional.of(sensorEntity));
		when(sensorEntity.getVersion()).thenReturn(VERSION);
	}

	@Test(expected = SensorNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(sensorJpaRepository.findById(ID)).thenReturn(Optional.<SensorEntity> empty());

		sensorRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test(expected = ConcurrentModificationException.class)
	public void failsWhenEntityWithIdHasDifferentVersion() {
		when(sensorEntity.getVersion()).thenReturn(DIFFERENT_VERSION);

		sensorRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		SensorEntity foundSensorEntity = sensorRepositoryImpl.getByIdAndVersion(ID, VERSION);

		assertThat(foundSensorEntity).isEqualTo(sensorEntity);

	}

}
