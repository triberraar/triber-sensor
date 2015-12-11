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
import be.tribersoft.sensor.domain.api.event.Event;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.domain.api.sensor.Sensor;

@RunWith(MockitoJUnitRunner.class)
public class EventUrlVisitorVisitReadingTest {
	private static final String DEVICE_ID = "deviceId";
	private static final String SENSOR_ID = "eradingId";
	private static final String EVENT_ID = "eventId";
	private static final String API_VERSION = "apiVersion";

	@InjectMocks
	private EventUrlVisitor visitor;
	@Mock
	private ReadingRepository readingRepository;
	protected MockHttpServletRequest request;

	@Mock
	private Event event;
	@Mock
	private Sensor sensor;
	@Mock
	private Device device;
	@Mock
	private Reading reading;

	@Before
	public void setUp() {
		Whitebox.setInternalState(visitor, API_VERSION, API_VERSION);
		when(event.getEventId()).thenReturn(EVENT_ID);
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);
	}

	@Test
	public void urlIsAbsentWhenEventIdDoesntExist() {
		when(readingRepository.exists(EVENT_ID)).thenReturn(false);

		visitor.visitReading(event);

		Optional<Link> link = visitor.getLink();
		assertThat(link.isPresent()).isFalse();
	}

	@Test
	public void urlWhenEventIdExists() {
		when(readingRepository.exists(EVENT_ID)).thenReturn(true);
		when(readingRepository.getById(EVENT_ID)).thenReturn(reading);
		when(reading.getSensor()).thenReturn(sensor);
		when(reading.getId()).thenReturn(EVENT_ID);
		when(sensor.getId()).thenReturn(SENSOR_ID);
		when(sensor.getDevice()).thenReturn(device);
		when(device.getId()).thenReturn(DEVICE_ID);

		visitor.visitReading(event);

		assertThat(visitor.getLink().isPresent()).isTrue();
		Link link = visitor.getLink().get();
		assertThat(link.getRel()).isEqualTo("readings");
		assertThat(link.getHref()).isEqualTo("http://localhost/api/apiVersion/device/" + DEVICE_ID + "/sensor/" + SENSOR_ID + "/reading?page=0");
	}

}
