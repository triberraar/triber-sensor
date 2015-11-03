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
public class SensorResourceGetTest {

	private static String ID = "id";

	@InjectMocks
	private SensorResource sensorResource;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private Sensor sensor;
	@Mock
	private SensorHateoasBuilder sensorHateosBuilder;
	@Mock
	private Resource<SensorToJsonAdapter> resource;

	@Before
	public void setUp() {
		when(sensorRepository.getById(ID)).thenReturn(sensor);
		when(sensorHateosBuilder.build(sensor)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<SensorToJsonAdapter> returnedResource = sensorResource.get(ID);

		assertThat(returnedResource).isSameAs(resource);
	}

}
