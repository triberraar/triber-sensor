package be.tribersoft.sensor.service.impl.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplUpdateTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private DeviceServiceImpl deviceService;
	@Mock
	private DeviceFacade deviceFacade;
	@Mock
	private DeviceMessage deviceMessage;

	@Test
	public void delegatesToFacade() {
		deviceService.update(ID, VERSION, deviceMessage);

		verify(deviceFacade).update(ID, VERSION, deviceMessage);
	}

}
