package be.tribersoft.sensor.rest.sensorReading;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingRepository;
import be.tribersoft.sensor.rest.sensor.SensorValidator;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingResourceAllTest {
	private static final String DEVICE_ID = "device id";
	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private SensorReadingResource sensorReadingResource;
	@Mock
	private SensorReadingRepository sensorReadingRepository;
	@Mock
	private SensorReading sensorReading1, sensorReading2;
	@Mock
	private SensorReadingHateoasBuilder sensorReadingHateosBuilder;
	@Mock
	private Resources<Resource<SensorReadingToJsonAdapter>> resources;
	@Mock
	private SensorValidator sensorValidator;

	@Before
	public void setUp() {
		doReturn(Arrays.<SensorReading> asList(sensorReading1, sensorReading2)).when(sensorReadingRepository).allBySensor(SENSOR_ID);
		when(sensorReadingHateosBuilder.build(Arrays.asList(sensorReading1, sensorReading2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<SensorReadingToJsonAdapter>> sensorResources = sensorReadingResource.all(DEVICE_ID, SENSOR_ID);

		assertThat(sensorResources).isSameAs(resources);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
	}

}
