package be.tribersoft.sensor.service.impl.device;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplDeleteTest {

	private static final long SENSOR_VERSION_1 = 3L;
	private static final long SENSOR_VERSION_2 = 4L;
	private static final String SENSOR_ID_1 = "sensor id 1";
	private static final String SENSOR_ID_2 = "sensor id 2";
	private static final long DEVICE_VERSION = 2L;
	private static final String DEVICE_ID = "id";
	@InjectMocks
	private DeviceServiceImpl deviceService;
	@Mock
	private DeviceFacade deviceFacade;
	@Mock
	private SensorFacade sensorFacade;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private Sensor sensor1, sensor2;

	@Before
	public void setup() {
		doReturn(Arrays.asList(sensor1, sensor2)).when(sensorRepository).allByDevice(DEVICE_ID);
		when(sensor1.getId()).thenReturn(SENSOR_ID_1);
		when(sensor1.getVersion()).thenReturn(SENSOR_VERSION_1);
		when(sensor2.getId()).thenReturn(SENSOR_ID_2);
		when(sensor2.getVersion()).thenReturn(SENSOR_VERSION_2);
	}

	@Test
	public void deletesDeviceAndAllAttachedSensors() {
		deviceService.delete(DEVICE_ID, DEVICE_VERSION);

		verify(deviceFacade).delete(DEVICE_ID, DEVICE_VERSION);
		verify(sensorFacade).delete(DEVICE_ID, SENSOR_ID_1, SENSOR_VERSION_1);
		verify(sensorFacade).delete(DEVICE_ID, SENSOR_ID_2, SENSOR_VERSION_2);
	}

}
