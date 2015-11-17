package be.tribersoft.sensor.rest.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.sensor.SensorValidator;

@RunWith(MockitoJUnitRunner.class)
public class ReadingResourceAllTest {
	private static final int PAGE = 2;
	private static final int PAGE_SIZE = 10;
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
	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;

	@Before
	public void setUp() {
		doReturn(Arrays.<Reading> asList(reading1, reading2)).when(readingRepository).allBySensor(eq(SENSOR_ID), pageableCaptor.capture());
		when(readingHateosBuilder.build(DEVICE_ID, SENSOR_ID, Arrays.asList(reading1, reading2), PAGE)).thenReturn(resources);
		Whitebox.setInternalState(readingResource, "pageSize", PAGE_SIZE);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = readingResource.all(DEVICE_ID, SENSOR_ID, PAGE);

		assertThat(sensorResources).isSameAs(resources);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
		Pageable pageable = pageableCaptor.getValue();
		assertThat(pageable.getPageNumber()).isEqualTo(PAGE);
		assertThat(pageable.getPageSize()).isEqualTo(PAGE_SIZE);
	}

}
