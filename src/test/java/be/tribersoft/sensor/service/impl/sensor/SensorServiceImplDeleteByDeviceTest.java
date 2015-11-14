package be.tribersoft.sensor.service.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplDeleteByDeviceTest {

	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorServiceImpl sensorServiceImpl;
	@Mock
	private SensorFacade sensorFacade;

	@Test
	public void delegatesToFacade() {
		sensorServiceImpl.deleteByDevice(DEVICE_ID);

		verify(sensorFacade).deleteByDevice(DEVICE_ID);

	}
}
