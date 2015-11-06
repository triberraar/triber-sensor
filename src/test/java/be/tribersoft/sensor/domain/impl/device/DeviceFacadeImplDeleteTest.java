package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceFacadeImplDeleteTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";

	@InjectMocks
	private DeviceFacadeImpl deviceFacade;

	@Mock
	private DeviceEntity device;
	@Mock
	private DeviceRepositoryImpl deviceRepositoryImpl;

	@Before
	public void setUp() {
		when(deviceRepositoryImpl.getByIdAndVersion(ID, VERSION)).thenReturn(device);
	}

	@Test
	public void savesCreatedDevice() {
		deviceFacade.delete(ID, VERSION);

		verify(deviceRepositoryImpl).delete(device);
	}

}
