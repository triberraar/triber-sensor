package be.tribersoft.sensor.domain.impl.event;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.Eventable;
import be.tribersoft.sensor.domain.api.event.exception.NotAnEventableException;

@RunWith(MockitoJUnitRunner.class)
public class EventListenerPostUpdateTest {

	@InjectMocks
	private EventListener eventListener;

	@Mock
	private Eventable eventable;

	@Mock
	private Object nonEventable;

	@Test
	public void callsVisitor() {
		eventListener.postUpdate(eventable);

		verify(eventable).accept(isA(EventVisitor.class), eq(EventMode.UPDATED));
	}

	@Test(expected = NotAnEventableException.class)
	public void failsWhenInputIsNotEventable() {
		eventListener.postUpdate(nonEventable);
	}

}
