package be.tribersoft.sensor.rest.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.device.DeviceService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceSaveTest {
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceService deviceService;
	@Mock
	private DevicePostJson devicePostJson;

	@Test
	public void delegatesToService() {
		deviceResource.save(devicePostJson);

		verify(deviceService).save(devicePostJson);
	}
}
