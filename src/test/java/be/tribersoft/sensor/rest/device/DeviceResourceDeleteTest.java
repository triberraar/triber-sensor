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
public class DeviceResourceDeleteTest {
	private static final String API_VERSION = "apiVersion";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceService deviceService;
	@Mock
	private DeviceDeleteJson deviceDeleteJson;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(deviceDeleteJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		deviceResource.delete(API_VERSION, ID, deviceDeleteJson);

		verify(deviceService).delete(ID, VERSION);
		verify(versionValidator).validate(API_VERSION);
	}
}
