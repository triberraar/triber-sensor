package be.tribersoft.sensor.service.impl.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplDeleteTest {

	private static final long DEVICE_VERSION = 2L;
	private static final String DEVICE_ID = "id";
	@InjectMocks
	private DeviceServiceImpl deviceService;
	@Mock
	private DeviceFacade deviceFacade;
	@Mock
	private SensorService sensorService;

	@Test
	public void deletesDeviceAndAllAttachedSensors() {
		deviceService.delete(DEVICE_ID, DEVICE_VERSION);

		verify(deviceFacade).delete(DEVICE_ID, DEVICE_VERSION);
		verify(sensorService).deleteByDevice(DEVICE_ID);
	}

}
