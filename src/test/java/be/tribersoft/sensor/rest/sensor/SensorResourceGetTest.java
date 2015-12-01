package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class SensorResourceGetTest {

	private static final String API_VERSION = "apiVersion";
	private static final String DEVICE_ID = "device id";
	private static String ID = "id";

	@InjectMocks
	private SensorResource sensorDeviceResource;
	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private Sensor sensor;
	@Mock
	private SensorHateoasBuilder sensorHateosBuilder;
	@Mock
	private Resource<SensorToJsonAdapter> resource;
	@Mock
	private SensorValidator sensorValidator;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(sensorRepository.getById(ID)).thenReturn(sensor);
		when(sensorHateosBuilder.build(sensor)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<SensorToJsonAdapter> returnedResource = sensorDeviceResource.get(API_VERSION, DEVICE_ID, ID);

		assertThat(returnedResource).isSameAs(resource);
		verify(sensorValidator).validate(DEVICE_ID, ID);
		verify(versionValidator).validate(API_VERSION);
	}

}
