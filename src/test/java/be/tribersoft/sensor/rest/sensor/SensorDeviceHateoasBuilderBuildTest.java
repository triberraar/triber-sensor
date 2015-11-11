package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
import be.tribersoft.sensor.domain.api.type.Type;
import be.tribersoft.sensor.domain.api.unit.Unit;

@RunWith(MockitoJUnitRunner.class)
public class SensorDeviceHateoasBuilderBuildTest {
	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static String NAME_1 = "name1";
	private static String NAME_2 = "name2";
	private static String DESCRIPTION_1 = "description1";
	private static String TYPE_ID_1 = "typeId1";
	private static String TYPE_ID_2 = "typeId2";
	private static String UNIT_ID_1 = "sensorId1";
	private static String UNIT_ID_2 = "sensorId2";
	private static String DEVICE_ID = "deviceId";

	protected MockHttpServletRequest request;

	private SensorDeviceHateoasBuilder builder = new SensorDeviceHateoasBuilder();

	@Mock
	private Sensor sensor1, sensor2;

	@Mock
	private Type type1, type2;
	@Mock
	private Unit unit1, unit2;
	@Mock
	private Device device;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(type1.getId()).thenReturn(TYPE_ID_1);
		when(type2.getId()).thenReturn(TYPE_ID_2);

		when(unit1.getId()).thenReturn(UNIT_ID_1);
		when(unit2.getId()).thenReturn(UNIT_ID_2);

		when(device.getId()).thenReturn(DEVICE_ID);

		when(sensor1.getId()).thenReturn(ID_1);
		when(sensor1.getName()).thenReturn(NAME_1);
		when(sensor1.getVersion()).thenReturn(VERSION_1);
		when(sensor1.getDescription()).thenReturn(Optional.of(DESCRIPTION_1));
		when(sensor1.getType()).thenReturn(type1);
		when(sensor1.getUnit()).thenReturn(unit1);
		when(sensor1.getDevice()).thenReturn(device);
		when(sensor1.getDescription()).thenReturn(Optional.of(DESCRIPTION_1));
		when(sensor2.getId()).thenReturn(ID_2);
		when(sensor2.getName()).thenReturn(NAME_2);
		when(sensor2.getVersion()).thenReturn(VERSION_2);
		when(sensor2.getDescription()).thenReturn(Optional.ofNullable(null));
		when(sensor2.getType()).thenReturn(type2);
		when(sensor2.getUnit()).thenReturn(unit2);
		when(sensor2.getDevice()).thenReturn(device);
	}

	@Test
	public void buildsLinksForASensor() {
		Resource<SensorToJsonAdapter> sensorResource = builder.build(sensor1);

		SensorToJsonAdapter content = sensorResource.getContent();
		assertThat(content.getId()).isEqualTo(ID_1);
		assertThat(content.getVersion()).isEqualTo(VERSION_1);
		assertThat(content.getName()).isEqualTo(NAME_1);
		assertThat(content.getDescription().get()).isEqualTo(DESCRIPTION_1);
		List<Link> links = sensorResource.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).endsWith("/device/" + DEVICE_ID + "/sensor/" + ID_1);
		assertThat(links.get(1).getRel()).isEqualTo("type");
		assertThat(links.get(1).getHref()).endsWith("/admin/type/" + TYPE_ID_1);
		assertThat(links.get(2).getRel()).isEqualTo("unit");
		assertThat(links.get(2).getHref()).endsWith("/admin/unit/" + UNIT_ID_1);
		assertThat(links.get(3).getRel()).isEqualTo("device");
		assertThat(links.get(3).getHref()).endsWith("/device/" + DEVICE_ID);
	}

	@Test
	public void buildsLinksForSensors() {
		Resources<Resource<SensorToJsonAdapter>> sensorResources = builder.build(Arrays.asList(sensor1, sensor2));

		List<Link> links = sensorResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).endsWith("/device/" + DEVICE_ID + "/sensor");

		assertThat(sensorResources.getContent().size()).isEqualTo(2);
		Collection<Resource<SensorToJsonAdapter>> content = sensorResources.getContent();
		Iterator<Resource<SensorToJsonAdapter>> iterator = content.iterator();

		Resource<SensorToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getName()).isEqualTo(NAME_1);
		assertThat(first.getContent().getDescription().get()).isEqualTo(DESCRIPTION_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(4);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(firstLinks.get(0).getHref()).endsWith("/device/" + DEVICE_ID + "/sensor/" + ID_1);
		assertThat(firstLinks.get(1).getRel()).isEqualTo("type");
		assertThat(firstLinks.get(1).getHref()).endsWith("/admin/type/" + TYPE_ID_1);
		assertThat(firstLinks.get(2).getRel()).isEqualTo("unit");
		assertThat(firstLinks.get(2).getHref()).endsWith("/admin/unit/" + UNIT_ID_1);
		assertThat(firstLinks.get(3).getRel()).isEqualTo("device");
		assertThat(firstLinks.get(3).getHref()).endsWith("/device/" + DEVICE_ID);

		Resource<SensorToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getId()).isEqualTo(ID_2);
		assertThat(second.getContent().getVersion()).isEqualTo(VERSION_2);
		assertThat(second.getContent().getName()).isEqualTo(NAME_2);
		assertThat(second.getContent().getDescription().isPresent()).isFalse();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(4);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(secondLinks.get(0).getHref()).endsWith("/device/" + DEVICE_ID + "/sensor/" + ID_2);
		assertThat(secondLinks.get(1).getRel()).isEqualTo("type");
		assertThat(secondLinks.get(1).getHref()).endsWith("/admin/type/" + TYPE_ID_2);
		assertThat(secondLinks.get(2).getRel()).isEqualTo("unit");
		assertThat(secondLinks.get(2).getHref()).endsWith("/admin/unit/" + UNIT_ID_2);
		assertThat(secondLinks.get(3).getRel()).isEqualTo("device");
		assertThat(secondLinks.get(3).getHref()).endsWith("/device/" + DEVICE_ID);
	}

}
