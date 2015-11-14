package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorFacadeImplDeleteByDeviceTest {

	private static final String DEVICE_ID = "device id";

	@InjectMocks
	private SensorFacadeImpl sensorFacadeImpl;

	@Mock
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Mock
	private SensorEntity sensor1, sensor2;

	@Before
	public void setUp() {
		when(sensorRepositoryImpl.allByDevice(DEVICE_ID)).thenReturn(Arrays.asList(sensor1, sensor2));
	}

	@Test
	public void deletesAllSensorsConnectedToDevice() {
		sensorFacadeImpl.deleteByDevice(DEVICE_ID);

		verify(sensorRepositoryImpl).delete(Arrays.asList(sensor1, sensor2));
	}
}
