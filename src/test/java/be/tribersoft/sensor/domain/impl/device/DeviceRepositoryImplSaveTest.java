package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceRepositoryImplSaveTest {

	@InjectMocks
	private DeviceRepositoryImpl deviceRepositoryImpl;

	@Mock
	private DeviceJpaRepository deviceJpaRepository;
	@Mock
	private DeviceEntity deviceEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		deviceRepositoryImpl.save(deviceEntity);

		verify(deviceJpaRepository).save(deviceEntity);
	}

}
