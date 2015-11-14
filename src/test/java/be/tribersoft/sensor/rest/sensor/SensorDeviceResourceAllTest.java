package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
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

import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@RunWith(MockitoJUnitRunner.class)
public class SensorDeviceResourceAllTest {

	private static final String DEVICE_ID = "device id";

	@InjectMocks
	private SensorDeviceResource sensorDeviceResource;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private Sensor sensor1, sensor2;
	@Mock
	private SensorDeviceHateoasBuilder sensorHateosBuilder;
	@Mock
	private Resources<Resource<SensorToJsonAdapter>> resources;

	@Before
	public void setUp() {
		doReturn(Arrays.asList(sensor1, sensor2)).when(sensorRepository).allByDevice(DEVICE_ID);
		when(sensorHateosBuilder.build(DEVICE_ID, Arrays.asList(sensor1, sensor2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<SensorToJsonAdapter>> returnedResource = sensorDeviceResource.all(DEVICE_ID);

		assertThat(returnedResource).isSameAs(resources);
	}

}
