package be.tribersoft.sensor.service.impl.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceImplDeleteTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorServiceImpl sensorService;
	@Mock
	private SensorFacade sensorFacade;

	@Test
	public void delegatesToFacade() {
		sensorService.delete(ID, VERSION);

		verify(sensorFacade).delete(ID, VERSION);
	}

}
