package be.tribersoft.sensor.rest.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.rest.sensor.SensorValidator;

@RunWith(MockitoJUnitRunner.class)
public class ReadingResourceAllTest {
	private static final String API_VERSION = "apiVersion";
	private static final int PAGE = 2;
	private static final int PAGE_SIZE = 10;
	private static final String DEVICE_ID = "device id";
	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private ReadingResource readingResource;
	@Mock
	private ReadingRepository readingRepository;
	@Mock
	private ReadingHateoasBuilder readingHateosBuilder;
	@Mock
	private SensorValidator sensorValidator;
	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;
	@Mock
	private VersionValidator versionValidator;
	@Mock
	private Page<? extends Reading> page;
	@Mock
	private PagedResources<Resource<ReadingToJsonAdapter>> pagedResources;

	@Before
	public void setUp() {
		doReturn(page).when(readingRepository).allBySensor(eq(SENSOR_ID), pageableCaptor.capture());
		when(readingHateosBuilder.build(DEVICE_ID, SENSOR_ID, page, PAGE)).thenReturn(pagedResources);
		Whitebox.setInternalState(readingResource, "pageSize", PAGE_SIZE);
	}

	@Test
	public void delegatesToService() {
		PagedResources<Resource<ReadingToJsonAdapter>> result = readingResource.all(API_VERSION, DEVICE_ID, SENSOR_ID, PAGE);

		assertThat(result).isSameAs(pagedResources);
		verify(sensorValidator).validate(DEVICE_ID, SENSOR_ID);
		Pageable pageable = pageableCaptor.getValue();
		assertThat(pageable.getPageNumber()).isEqualTo(PAGE);
		assertThat(pageable.getPageSize()).isEqualTo(PAGE_SIZE);
		verify(versionValidator).validate(API_VERSION);
	}

}
