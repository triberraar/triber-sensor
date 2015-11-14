package be.tribersoft.sensor.service.impl.sensor;

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

import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplDeleteByDeviceTest {

	private static final long VERSION_2 = 2L;
	private static final long VERSION_1 = 1L;
	private static final String SENSOR_ID_2 = "sensor id 2";
	private static final String SENSOR_ID_1 = "sensor id 1";
	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorServiceImpl sensorServiceImpl;
	@Mock
	private SensorFacade sensorFacade;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private ReadingService readingService;
	@Mock
	private Sensor sensor1, sensor2;

	@Before
	public void setUp() {
		doReturn(Arrays.asList(sensor1, sensor2)).when(sensorRepository).allByDevice(DEVICE_ID);
		when(sensor1.getId()).thenReturn(SENSOR_ID_1);
		when(sensor1.getVersion()).thenReturn(VERSION_1);
		when(sensor2.getId()).thenReturn(SENSOR_ID_2);
		when(sensor2.getVersion()).thenReturn(VERSION_2);

	}

	@Test
	public void deletesAllSensors() {
		sensorServiceImpl.deleteByDevice(DEVICE_ID);

		verify(sensorFacade).delete(SENSOR_ID_1, VERSION_1);
		verify(sensorFacade).delete(SENSOR_ID_2, VERSION_2);
		verify(readingService).deleteBySensor(SENSOR_ID_1);
		verify(readingService).deleteBySensor(SENSOR_ID_2);

	}
}
