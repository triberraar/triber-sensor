package be.tribersoft.sensor.service.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplSaveTest {

	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorServiceImpl sensorService;
	@Mock
	private SensorFacade sensorFacade;
	@Mock
	private SensorMessage sensorMessage;

	@Test
	public void delegatesToFacade() {
		sensorService.save(DEVICE_ID, sensorMessage);

		verify(sensorFacade).save(DEVICE_ID, sensorMessage);
	}

}
