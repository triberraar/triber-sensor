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

@RunWith(MockitoJUnitRunner.class)
public class DeviceRepositoryImplGetByIdTest {

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
	}

	@Test(expected = DeviceNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(deviceJpaRepository.findById(ID)).thenReturn(Optional.<DeviceEntity> empty());

		deviceRepositoryImpl.getById(ID);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		DeviceEntity foundDeviceEntity = deviceRepositoryImpl.getById(ID);

		assertThat(foundDeviceEntity).isEqualTo(deviceEntity);

	}

}
