package be.tribersoft.sensor.rest.reading;

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

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.reading.ReadingHateoasBuilder;
import be.tribersoft.sensor.rest.reading.ReadingResource;
import be.tribersoft.sensor.rest.reading.ReadingToJsonAdapter;
import be.tribersoft.sensor.rest.sensor.SensorValidator;

@RunWith(MockitoJUnitRunner.class)
public class ReadingResourceAllTest {
	private static final String DEVICE_ID = "device id";
	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private ReadingResource readingResource;
	@Mock
	private ReadingRepository readingRepository;
	@Mock
	private Reading reading1, reading2;
	@Mock
	private ReadingHateoasBuilder readingHateosBuilder;
	@Mock
	private Resources<Resource<ReadingToJsonAdapter>> resources;
	@Mock
	private SensorValidator sensorValidator;

	@Before
	public void setUp() {
		doReturn(Arrays.<Reading> asList(reading1, reading2)).when(readingRepository).allBySensor(SENSOR_ID);
		when(readingHateosBuilder.build(Arrays.asList(reading1, reading2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = readingResource.all(DEVICE_ID, SENSOR_ID);

		assertThat(sensorResources).isSameAs(resources);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
	}

}
