package be.tribersoft.sensor.rest.sensor;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@RunWith(MockitoJUnitRunner.class)
public class SensorValidatorValidateTest {

	private static final String ID = "id";
	private static final String DEVICE_ID = "device id";
	@InjectMocks
	private SensorValidator sensorValidator;
	@Mock
	private SensorRepository sensorRepository;

	@Test
	public void validatesIfSensorExists() {
		sensorValidator.validate(DEVICE_ID, ID);

		verify(sensorRepository).getByDeviceIdAndId(DEVICE_ID, ID);
	}
}
