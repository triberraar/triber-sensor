package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@RunWith(MockitoJUnitRunner.class)
public class DeviceFacadeImplUpdateTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";

	@InjectMocks
	private DeviceFacadeImpl deviceFacade;

	@Mock
	private DeviceUpdater deviceUpdater;
	@Mock
	private DeviceMessage deviceMessage;
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
		deviceFacade.update(ID, VERSION, deviceMessage);

		verify(deviceUpdater).update(device, deviceMessage);
	}

}
