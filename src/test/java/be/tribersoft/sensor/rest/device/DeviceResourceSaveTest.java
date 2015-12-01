package be.tribersoft.sensor.rest.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.device.DeviceService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceSaveTest {
	private static final String API_VERSION = "apiVersion";
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceService deviceService;
	@Mock
	private DevicePostJson devicePostJson;
	@Mock
	private VersionValidator versionValidator;

	@Test
	public void delegatesToService() {
		deviceResource.save(API_VERSION, devicePostJson);

		verify(deviceService).save(devicePostJson);
		verify(versionValidator).validate(API_VERSION);
	}
}
