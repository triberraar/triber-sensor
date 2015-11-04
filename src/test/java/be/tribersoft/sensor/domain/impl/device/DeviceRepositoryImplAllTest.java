package be.tribersoft.sensor.domain.impl.device;

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
public class DeviceRepositoryImplAllTest {

	@InjectMocks
	private DeviceRepositoryImpl deviceRepositoryImpl;

	@Mock
	private DeviceJpaRepository deviceJpaRepository;
	@Mock
	private DeviceEntity deviceEntity1, deviceEntity2;

	@Before
	public void setUp() {
		when(deviceJpaRepository.findAllByOrderByCreationDateDesc()).thenReturn(Arrays.asList(deviceEntity1, deviceEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<DeviceEntity> all = deviceRepositoryImpl.all();

		verify(deviceJpaRepository).findAllByOrderByCreationDateDesc();
		assertThat(all).isEqualTo(Arrays.asList(deviceEntity1, deviceEntity2));
	}

}
