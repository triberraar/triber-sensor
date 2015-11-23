package be.tribersoft.sensor.rest.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@RunWith(MockitoJUnitRunner.class)
public class ReadingHateoasBuilderBuildTest {
	private static final int SIZE = 42;
	private static final long TOTAL_ELEMENTS = 22L;
	private static final int NUMBER = 2;
	private static final String API_VERSION = "apiVersion";
	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static BigDecimal VALUE_1 = BigDecimal.valueOf(123.9);
	private static BigDecimal VALUE_2 = BigDecimal.valueOf(763.9);
	private static String DEVICE_ID = "deviceId";
	private static String SENSOR_ID = "sensorId";
	private static int PAGE = 0;

	protected MockHttpServletRequest request;

	@InjectMocks
	private ReadingHateoasBuilder builder;

	@Mock
	private Reading reading1, reading2;
	@Mock
	private Sensor sensor;
	@Mock
	private Device device;
	@Mock
	private ReadingRepository readingRepository;
	@Mock
	private Page<? extends Reading> page;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(device.getId()).thenReturn(DEVICE_ID);
		when(sensor.getDevice()).thenReturn(device);
		when(sensor.getId()).thenReturn(SENSOR_ID);
		doReturn(Arrays.asList(reading1, reading2)).when(page).getContent();
		when(page.getNumber()).thenReturn(NUMBER);
		when(page.getTotalElements()).thenReturn(TOTAL_ELEMENTS);
		when(page.getSize()).thenReturn(SIZE);

		when(reading1.getId()).thenReturn(ID_1);
		when(reading1.getVersion()).thenReturn(VERSION_1);
		when(reading1.getSensor()).thenReturn(sensor);
		when(reading1.getValue()).thenReturn(VALUE_1);
		when(reading2.getId()).thenReturn(ID_2);
		when(reading2.getVersion()).thenReturn(VERSION_2);
		when(reading2.getSensor()).thenReturn(sensor);
		when(reading2.getValue()).thenReturn(VALUE_2);
		Whitebox.setInternalState(builder, API_VERSION, API_VERSION);
	}

	@Test
	public void buildsLinksAndMetaDataForReadings() {
		PagedResources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, page, PAGE);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(3);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=21");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<ReadingToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<ReadingToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<ReadingToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getValue()).isEqualTo(VALUE_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
		Resource<ReadingToJsonAdapter> second = iterator.next();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);

		assertThat(sensorResources.getMetadata().getNumber()).isEqualTo(NUMBER);
		assertThat(sensorResources.getMetadata().getSize()).isEqualTo(SIZE);
		assertThat(sensorResources.getMetadata().getTotalElements()).isEqualTo(TOTAL_ELEMENTS);
		assertThat(sensorResources.getMetadata().getTotalPages()).isEqualTo(1L);

		sensorResources.getNextLink();
	}

	@Test
	public void addsPreviousLinkWhenNotOnFirstPage() {
		when(page.hasPrevious()).thenReturn(true);
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, page, 1);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_PREVIOUS);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=21");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<ReadingToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<ReadingToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<ReadingToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getValue()).isEqualTo(VALUE_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
		Resource<ReadingToJsonAdapter> second = iterator.next();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
	}

	@Test
	public void addsNextLinkWhenMoreItemsExist() {
		when(page.hasNext()).thenReturn(true);
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, page, 0);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_NEXT);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=21");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<ReadingToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<ReadingToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<ReadingToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getValue()).isEqualTo(VALUE_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
		Resource<ReadingToJsonAdapter> second = iterator.next();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
	}

	@Test
	public void addsNextLinkWhenMoreItemsExistAndPreviousWhenNotOnFirstPage() {
		when(page.hasNext()).thenReturn(true);
		when(page.hasPrevious()).thenReturn(true);
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, page, 1);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(5);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_PREVIOUS);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_NEXT);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=2");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(4).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(4).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=21");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<ReadingToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<ReadingToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<ReadingToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getValue()).isEqualTo(VALUE_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
		Resource<ReadingToJsonAdapter> second = iterator.next();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(SensorToJsonAdapter.SENSOR);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID);
	}

}
