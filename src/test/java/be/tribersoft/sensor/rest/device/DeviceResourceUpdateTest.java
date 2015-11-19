package be.tribersoft.sensor.rest.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.device.DeviceService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceUpdateTest {
	private static final String API_VERSION = "apiVersion";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceService deviceService;
	@Mock
	private DeviceUpdateJson deviceUpdateJson;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(deviceUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		deviceResource.update(API_VERSION, ID, deviceUpdateJson);

		verify(deviceService).update(ID, VERSION, deviceUpdateJson);
		verify(versionValidator).validate(API_VERSION);
	}
}
