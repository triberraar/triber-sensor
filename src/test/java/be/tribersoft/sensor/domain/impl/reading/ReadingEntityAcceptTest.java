package be.tribersoft.sensor.domain.impl.reading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.impl.event.EventVisitor;

@RunWith(MockitoJUnitRunner.class)
public class ReadingEntityAcceptTest {

	private static final EventMode EVENT_MODE = EventMode.DELETE;
	private ReadingEntity readingEntity = new ReadingEntity();
	@Mock
	private EventVisitor eventVisitor;

	@Test
	public void usesVisitor() {
		readingEntity.accept(eventVisitor, EVENT_MODE);

		verify(eventVisitor).visit(readingEntity, EVENT_MODE);
	}
}
