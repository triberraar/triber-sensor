package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorFacadeImplSaveTest {

	@InjectMocks
	private SensorFacadeImpl sensorFacade;

	@Mock
	private SensorFactory sensorFactory;
	@Mock
	private SensorRepositoryImpl sensorRepositoryImpl;
	@Mock
	private SensorMessage sensorMessage;
	@Mock
	private SensorEntity sensor;

	@Before
	public void setUp() {
		when(sensorFactory.create(sensorMessage)).thenReturn(sensor);
	}

	@Test
	public void savesCreatedSensor() {
		sensorFacade.save(sensorMessage);

		verify(sensorRepositoryImpl).save(sensor);
	}

}
