package be.tribersoft.sensor.domain.impl.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.event.Eventable;

@RunWith(MockitoJUnitRunner.class)
public class EventDocumentConstructorTest {

	private static final String ID = "id";
	private static final EventSubject EVENT_SUBJECT = EventSubject.READING;
	private static final EventMode EVENT_MODE = EventMode.DELETE;
	private static final LocalDateTime DATE = LocalDateTime.now();

	@Mock
	private Eventable eventable;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
		when(eventable.getId()).thenReturn(ID);
	}

	@Test
	public void constructsCorrectly() {
		EventDocument eventDocument = new EventDocument(eventable, EVENT_MODE, EVENT_SUBJECT);

		assertThat(eventDocument.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
		assertThat(eventDocument.getEventId()).isEqualTo(ID);
		assertThat(eventDocument.getId()).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
		assertThat(eventDocument.getEventSubject()).isEqualTo(EVENT_SUBJECT);
		assertThat(eventDocument.getEventMode()).isEqualTo(EVENT_MODE);
	}

}
