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
public class SensorRepositoryImplGetDeviceIdAndByIdTest {

	private static final long DIFFERENT_VERSION = 3L;
	private static final String ID = "id";
	private static final String DEVICE_ID = "device id";

	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorJpaRepository sensorJpaRepository;
	@Mock
	private SensorEntity sensorEntity;

	@Before
	public void setUp() {
		when(sensorJpaRepository.findByDeviceIdAndId(DEVICE_ID, ID)).thenReturn(Optional.of(sensorEntity));
	}

	@Test(expected = SensorNotFoundException.class)
	public void failsWhenNoEntityWithDeviceIdAndId() {
		when(sensorJpaRepository.findByDeviceIdAndId(DEVICE_ID, ID)).thenReturn(Optional.<SensorEntity> empty());

		sensorRepositoryImpl.getByDeviceIdAndId(DEVICE_ID, ID);
	}

	@Test
	public void returnsEntityWithId() {
		SensorEntity foundSensorEntity = sensorRepositoryImpl.getByDeviceIdAndId(DEVICE_ID, ID);

		assertThat(foundSensorEntity).isEqualTo(sensorEntity);
	}
}
