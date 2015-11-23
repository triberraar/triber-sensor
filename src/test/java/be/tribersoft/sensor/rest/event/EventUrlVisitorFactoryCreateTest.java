package be.tribersoft.sensor.rest.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EventUrlVisitorFactoryCreateTest {

	private EventUrlVisitorFactory eventUrlVisitorFactory = new EventUrlVisitorFactory();

	@Test
	public void returnsNewEventUrlVisitor() {
		assertThat(eventUrlVisitorFactory.create()).isNotNull();
	}
}
