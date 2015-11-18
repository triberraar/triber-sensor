package be.tribersoft.sensor.rest.device;

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
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.device.Device;

@RunWith(MockitoJUnitRunner.class)
public class DeviceHateoasBuilderBuildTest {
	private static final String API_VERSION = "apiVersion";
	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static String NAME_1 = "name1";
	private static String NAME_2 = "name2";
	private static String DESCRIPTION_1 = "description1";
	private static String LOCATION_1 = "location1";

	protected MockHttpServletRequest request;

	private DeviceHateoasBuilder builder = new DeviceHateoasBuilder();

	@Mock
	private Device device1, device2;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(device1.getId()).thenReturn(ID_1);
		when(device1.getName()).thenReturn(NAME_1);
		when(device1.getVersion()).thenReturn(VERSION_1);
		when(device1.getDescription()).thenReturn(Optional.of(DESCRIPTION_1));
		when(device1.getLocation()).thenReturn(Optional.of(LOCATION_1));
		when(device1.getDescription()).thenReturn(Optional.of(DESCRIPTION_1));
		when(device2.getId()).thenReturn(ID_2);
		when(device2.getName()).thenReturn(NAME_2);
		when(device2.getVersion()).thenReturn(VERSION_2);
		when(device2.getDescription()).thenReturn(Optional.ofNullable(null));
		when(device2.getLocation()).thenReturn(Optional.ofNullable(null));
		Whitebox.setInternalState(builder, API_VERSION, API_VERSION);
	}

	@Test
	public void buildsLinksForADevice() {
		Resource<DeviceToJsonAdapter> deviceResource = builder.build(device1);

		DeviceToJsonAdapter content = deviceResource.getContent();
		assertThat(content.getId()).isEqualTo(ID_1);
		assertThat(content.getVersion()).isEqualTo(VERSION_1);
		assertThat(content.getName()).isEqualTo(NAME_1);
		assertThat(content.getDescription().get()).isEqualTo(DESCRIPTION_1);
		assertThat(content.getLocation().get()).isEqualTo(LOCATION_1);
		List<Link> links = deviceResource.getLinks();
		assertThat(links.size()).isEqualTo(2);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_1);
		assertThat(links.get(1).getRel()).isEqualTo("sensors");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_1 + "/sensor");

	}

	@Test
	public void buildsLinksForDevices() {
		Resources<Resource<DeviceToJsonAdapter>> deviceResources = builder.build(Arrays.asList(device1, device2));

		List<Link> links = deviceResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device");

		assertThat(deviceResources.getContent().size()).isEqualTo(2);
		Collection<Resource<DeviceToJsonAdapter>> content = deviceResources.getContent();
		Iterator<Resource<DeviceToJsonAdapter>> iterator = content.iterator();

		Resource<DeviceToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getName()).isEqualTo(NAME_1);
		assertThat(first.getContent().getDescription().get()).isEqualTo(DESCRIPTION_1);
		assertThat(first.getContent().getLocation().get()).isEqualTo(LOCATION_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(2);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_1);
		assertThat(firstLinks.get(1).getRel()).isEqualTo("sensors");
		assertThat(firstLinks.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_1 + "/sensor");

		Resource<DeviceToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getId()).isEqualTo(ID_2);
		assertThat(second.getContent().getVersion()).isEqualTo(VERSION_2);
		assertThat(second.getContent().getName()).isEqualTo(NAME_2);
		assertThat(second.getContent().getDescription().isPresent()).isFalse();
		assertThat(second.getContent().getLocation().isPresent()).isFalse();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(2);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_2);
		assertThat(secondLinks.get(1).getRel()).isEqualTo("sensors");
		assertThat(secondLinks.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/device/" + ID_2 + "/sensor");
	}

}
