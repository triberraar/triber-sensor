package be.tribersoft.sensor.rest.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.device.DeviceService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceUpdateTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceService deviceService;
	@Mock
	private DeviceUpdateJson deviceUpdateJson;

	@Before
	public void setUp() {
		when(deviceUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		deviceResource.update(ID, deviceUpdateJson);

		verify(deviceService).update(ID, VERSION, deviceUpdateJson);
	}
}
