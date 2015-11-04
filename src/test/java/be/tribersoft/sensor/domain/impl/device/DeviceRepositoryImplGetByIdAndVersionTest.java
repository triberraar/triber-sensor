package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.exception.DeviceNotFoundException;
import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;

@RunWith(MockitoJUnitRunner.class)
public class DeviceRepositoryImplGetByIdAndVersionTest {

	private static final long DIFFERENT_VERSION = 3L;
	private static final long VERSION = 2l;
	private static final String ID = "id";

	@InjectMocks
	private DeviceRepositoryImpl deviceRepositoryImpl;

	@Mock
	private DeviceJpaRepository deviceJpaRepository;
	@Mock
	private DeviceEntity deviceEntity;

	@Before
	public void setUp() {
		when(deviceJpaRepository.findById(ID)).thenReturn(Optional.of(deviceEntity));
		when(deviceEntity.getVersion()).thenReturn(VERSION);
	}

	@Test(expected = DeviceNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(deviceJpaRepository.findById(ID)).thenReturn(Optional.<DeviceEntity> empty());

		deviceRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test(expected = ConcurrentModificationException.class)
	public void failsWhenEntityWithIdHasDifferentVersion() {
		when(deviceEntity.getVersion()).thenReturn(DIFFERENT_VERSION);

		deviceRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		DeviceEntity foundDeviceEntity = deviceRepositoryImpl.getByIdAndVersion(ID, VERSION);

		assertThat(foundDeviceEntity).isEqualTo(deviceEntity);

	}

}
