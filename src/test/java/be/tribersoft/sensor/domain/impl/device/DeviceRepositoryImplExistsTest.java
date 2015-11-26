package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceRepositoryImplExistsTest {

	private static final String ID = "id";
	@InjectMocks
	private DeviceRepositoryImpl deviceRepositoryImpl;
	@Mock
	private DeviceJpaRepository deviceJpaRepository;

	@Before
	public void setup() {
		when(deviceJpaRepository.exists(ID)).thenReturn(true);
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(deviceRepositoryImpl.exists(ID)).isTrue();
	}
}
