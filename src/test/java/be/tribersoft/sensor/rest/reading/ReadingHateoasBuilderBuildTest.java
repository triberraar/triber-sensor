package be.tribersoft.sensor.rest.reading;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.hateoas.Link;
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
	private static final String API_VERSION = "apiVersion";
	private static final int PAGE_SIZE = 20;
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

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(device.getId()).thenReturn(DEVICE_ID);
		when(sensor.getDevice()).thenReturn(device);
		when(sensor.getId()).thenReturn(SENSOR_ID);

		when(reading1.getId()).thenReturn(ID_1);
		when(reading1.getVersion()).thenReturn(VERSION_1);
		when(reading1.getSensor()).thenReturn(sensor);
		when(reading1.getValue()).thenReturn(VALUE_1);
		when(reading2.getId()).thenReturn(ID_2);
		when(reading2.getVersion()).thenReturn(VERSION_2);
		when(reading2.getSensor()).thenReturn(sensor);
		when(reading2.getValue()).thenReturn(VALUE_2);
		when(readingRepository.countBySensor(SENSOR_ID)).thenReturn(1);
		Whitebox.setInternalState(builder, "pageSize", PAGE_SIZE);
		Whitebox.setInternalState(builder, API_VERSION, API_VERSION);
	}

	@Test
	public void buildsLinksForReadingss() {
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, Arrays.asList(reading1, reading2), PAGE);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");

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
	public void addsPreviousLinkWhenNotOnFirstPage() {
		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, Arrays.asList(reading1, reading2), 1);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(2);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");
		assertThat(links.get(1).getRel()).isEqualTo("previous");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");

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
		when(readingRepository.countBySensor(SENSOR_ID)).thenReturn(PAGE_SIZE + 1);

		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, Arrays.asList(reading1, reading2), 0);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(2);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(1).getRel()).isEqualTo("next");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");

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
		when(readingRepository.countBySensor(SENSOR_ID)).thenReturn((PAGE_SIZE * 2) + 1);

		Resources<Resource<ReadingToJsonAdapter>> sensorResources = builder.build(DEVICE_ID, SENSOR_ID, Arrays.asList(reading1, reading2), 1);

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(3);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=1");
		assertThat(links.get(1).getRel()).isEqualTo("previous");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
		assertThat(links.get(2).getRel()).isEqualTo("next");
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=2");

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
