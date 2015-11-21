package be.tribersoft.sensor.domain.impl.event;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.Eventable;
import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

@RunWith(MockitoJUnitRunner.class)
public class EventListenerPostPersistTest {

	@InjectMocks
	private EventListener eventListener;

	@Mock
	private EventRepositoryImpl eventRepositoryImpl;

	@Mock
	private EventFactory eventFactory;

	@Mock
	private Eventable eventable;

	@Mock
	private EventDocument eventDocument;

	@Mock
	private Object nonEventable;

	@Before
	public void setUp() {
		when(eventFactory.create(eventable, EventMode.CREATE)).thenReturn(eventDocument);
	}

	@Test
	public void createsAndSavesAnEventDocument() {
		eventListener.postPersist(eventable);

		verify(eventRepositoryImpl).save(eventDocument);
	}

	@Test(expected = NotAnEventableException.class)
	public void failsWhenInputIsNotEventable() {
		eventListener.postPersist(nonEventable);
	}

}
