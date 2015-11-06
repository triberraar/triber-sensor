package be.tribersoft.sensor.service.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplUpdateTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorServiceImpl sensorService;
	@Mock
	private SensorFacade sensorFacade;
	@Mock
	private SensorUpdateMessage sensorUpdateMessage;

	@Test
	public void delegatesToFacade() {
		sensorService.update(ID, VERSION, sensorUpdateMessage);

		verify(sensorFacade).update(ID, VERSION, sensorUpdateMessage);
	}

}
