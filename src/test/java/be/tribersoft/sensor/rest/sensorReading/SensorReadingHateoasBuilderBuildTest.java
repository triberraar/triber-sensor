package be.tribersoft.sensor.rest.sensorReading;

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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingHateoasBuilderBuildTest {
	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static BigDecimal VALUE_1 = BigDecimal.valueOf(123.9);
	private static BigDecimal VALUE_2 = BigDecimal.valueOf(763.9);
	private static String DEVICE_ID = "deviceId";
	private static String SENSOR_ID = "sensorId";

	protected MockHttpServletRequest request;

	private SensorReadingHateoasBuilder builder = new SensorReadingHateoasBuilder();

	@Mock
	private SensorReading sensorReading1, sensorReading2;
	@Mock
	private Sensor sensor;
	@Mock
	private Device device;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(device.getId()).thenReturn(DEVICE_ID);
		when(sensor.getDevice()).thenReturn(device);
		when(sensor.getId()).thenReturn(SENSOR_ID);

		when(sensorReading1.getId()).thenReturn(ID_1);
		when(sensorReading1.getVersion()).thenReturn(VERSION_1);
		when(sensorReading1.getSensor()).thenReturn(sensor);
		when(sensorReading1.getValue()).thenReturn(VALUE_1);
		when(sensorReading2.getId()).thenReturn(ID_2);
		when(sensorReading2.getVersion()).thenReturn(VERSION_2);
		when(sensorReading2.getSensor()).thenReturn(sensor);
		when(sensorReading2.getValue()).thenReturn(VALUE_2);
	}

	@Test
	public void buildsLinksForSensorReadingss() {
		Resources<Resource<SensorReadingToJsonAdapter>> sensorResources = builder.build(Arrays.asList(sensorReading1, sensorReading2));

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).endsWith("/reading");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<SensorReadingToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<SensorReadingToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<SensorReadingToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getValue()).isEqualTo(VALUE_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(0);
		Resource<SensorReadingToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getVersion()).isEqualTo(VERSION_2);
		assertThat(second.getContent().getValue()).isEqualTo(VALUE_2);
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(0);
	}

}
