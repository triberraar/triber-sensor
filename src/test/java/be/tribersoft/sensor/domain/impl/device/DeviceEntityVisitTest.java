package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.impl.event.EventVisitor;

@RunWith(MockitoJUnitRunner.class)
public class DeviceEntityVisitTest {

	private static final EventMode EVENT_MODE = EventMode.DELETED;
	private DeviceEntity deviceEntity = new DeviceEntity();
	@Mock
	private EventVisitor eventVisitor;

	@Test
	public void usesVisitor() {
		deviceEntity.accept(eventVisitor, EVENT_MODE);

		verify(eventVisitor).visit(deviceEntity, EVENT_MODE);
	}
}
