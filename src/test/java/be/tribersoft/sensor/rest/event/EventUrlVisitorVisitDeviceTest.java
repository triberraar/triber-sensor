package be.tribersoft.sensor.rest.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.device.DeviceRepository;
import be.tribersoft.sensor.domain.api.event.Event;

@RunWith(MockitoJUnitRunner.class)
public class EventUrlVisitorVisitDeviceTest {
	private static final String DEVICE_ID = "deviceId";
	private static final String EVENT_ID = "eventId";
	private static final String API_VERSION = "apiVersion";

	@InjectMocks
	private EventUrlVisitor visitor;
	@Mock
	private DeviceRepository deviceRepository;
	protected MockHttpServletRequest request;

	@Mock
	private Event event;
	@Mock
	private Device device;

	@Before
	public void setup() {
		Whitebox.setInternalState(visitor, API_VERSION, API_VERSION);
		when(event.getEventId()).thenReturn(EVENT_ID);
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);
	}

	@Test
	public void urlIsAbsentWhenEventIdDoesntExist() {
		when(deviceRepository.exists(EVENT_ID)).thenReturn(false);

		visitor.visitDevice(event);

		Optional<Link> link = visitor.getLink();
		assertThat(link.isPresent()).isFalse();
	}

	@Test
	public void urlWhenEventIdExists() {
		when(deviceRepository.exists(EVENT_ID)).thenReturn(true);
		when(deviceRepository.getById(EVENT_ID)).thenReturn(device);
		when(device.getId()).thenReturn(DEVICE_ID);

		visitor.visitDevice(event);

		assertThat(visitor.getLink().isPresent()).isTrue();
		Link link = visitor.getLink().get();
		assertThat(link.getRel()).isEqualTo("device");
		assertThat(link.getHref()).isEqualTo("http://localhost/api/apiVersion/device/" + DEVICE_ID);
	}

}
