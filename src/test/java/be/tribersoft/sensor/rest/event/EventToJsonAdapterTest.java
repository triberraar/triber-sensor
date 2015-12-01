package be.tribersoft.sensor.rest.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.Event;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventToJsonAdapterTest {

	private static final EventSubject EVENT_SUBJECT = EventSubject.READING;
	private static final EventMode EVENT_MODE = EventMode.DELETED;
	private static final Date CREATION_DATE = new Date();
	private static final String EVENT_ID = "event id";
	@Mock
	private Event event;

	@Before
	public void setUp() {
		when(event.getEventId()).thenReturn(EVENT_ID);
		when(event.getCreationDate()).thenReturn(CREATION_DATE);
		when(event.getEventMode()).thenReturn(EVENT_MODE);
		when(event.getEventSubject()).thenReturn(EVENT_SUBJECT);
	}

	@Test
	public void delegatesToEvent() {
		EventToJsonAdapter eventToJsonAdapter = new EventToJsonAdapter(event);

		assertThat(eventToJsonAdapter.getEventId()).isEqualTo(EVENT_ID);
		assertThat(eventToJsonAdapter.getCreationDate()).isEqualTo(CREATION_DATE);
		assertThat(eventToJsonAdapter.getEventMode()).isEqualTo(EVENT_MODE.getMessage());
		assertThat(eventToJsonAdapter.getEventSubject()).isEqualTo(EVENT_SUBJECT.getMessage());
	}
}
