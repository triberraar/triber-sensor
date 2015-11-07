package be.tribersoft.sensor.domain.impl.sensor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

@RunWith(MockitoJUnitRunner.class)
public class SensorFacadeImplUpdateTest {

	private static final String DEVICE_ID = "device id";
	private static final long VERSION = 2L;
	private static final String ID = "id";

	@InjectMocks
	private SensorFacadeImpl sensorFacade;

	@Mock
	private SensorUpdater sensorUpdater;
	@Mock
	private SensorUpdateMessage sensorUpdateMessage;
	@Mock
	private SensorEntity sensor;
	@Mock
	private SensorRepositoryImpl sensorRepositoryImpl;

	@Before
	public void setUp() {
		when(sensorRepositoryImpl.getByDeviceIdAndIdAndVersion(DEVICE_ID, ID, VERSION)).thenReturn(sensor);
	}

	@Test
	public void savesCreatedSensor() {
		sensorFacade.update(DEVICE_ID, ID, VERSION, sensorUpdateMessage);

		verify(sensorUpdater).update(sensor, sensorUpdateMessage);
	}

}
