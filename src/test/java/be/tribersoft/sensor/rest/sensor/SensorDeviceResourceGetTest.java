package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@RunWith(MockitoJUnitRunner.class)
public class SensorDeviceResourceGetTest {

	private static final String DEVICE_ID = "device id";
	private static String ID = "id";

	@InjectMocks
	private SensorDeviceResource sensorDeviceResource;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private Sensor sensor;
	@Mock
	private SensorDeviceHateoasBuilder sensorHateosBuilder;
	@Mock
	private Resource<SensorToJsonAdapter> resource;

	@Before
	public void setUp() {
		when(sensorRepository.getByDeviceIdAndId(DEVICE_ID, ID)).thenReturn(sensor);
		when(sensorHateosBuilder.build(sensor)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<SensorToJsonAdapter> returnedResource = sensorDeviceResource.get(DEVICE_ID, ID);

		assertThat(returnedResource).isSameAs(resource);
	}

}
