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
public class SensorResourceAllTest {

	@InjectMocks
	private SensorResource sensorResource;
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
		doReturn(Arrays.<Sensor> asList(sensor1, sensor2)).when(sensorRepository).all();
		when(sensorHateosBuilder.build(Arrays.asList(sensor1, sensor2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<SensorToJsonAdapter>> sensorResources = sensorResource.all();

		assertThat(sensorResources).isSameAs(resources);
	}

}
