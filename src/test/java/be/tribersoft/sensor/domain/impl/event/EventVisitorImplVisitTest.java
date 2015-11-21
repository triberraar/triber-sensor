package be.tribersoft.sensor.domain.impl.event;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.sensor.Sensor;

@RunWith(MockitoJUnitRunner.class)
public class EventVisitorImplVisitTest {

	private static final EventMode EVENT_MODE = EventMode.DELETE;
	@InjectMocks
	private EventVisitorImpl eventVisitorImpl;
	@Mock
	private Reading reading;
	@Mock
	private Sensor sensor;
	@Mock
	private Device device;
	@Mock
	private EventRepositoryImpl eventRepositoryImpl;
	@Mock
	private EventFactory eventFactory;
	private EventDocument readingEventDocument, deviceEventDocument, sensorEventDocument;

	@Before
	public void setUp() {
		when(eventFactory.create(reading, EVENT_MODE, EventSubject.READING)).thenReturn(readingEventDocument);
		when(eventFactory.create(reading, EVENT_MODE, EventSubject.DEVICE)).thenReturn(deviceEventDocument);
		when(eventFactory.create(reading, EVENT_MODE, EventSubject.SENSOR)).thenReturn(sensorEventDocument);
	}

	@Test
	public void visitWithReading_createsAndStoresAEvent() {
		eventVisitorImpl.visit(reading, EVENT_MODE);

		verify(eventRepositoryImpl).save(readingEventDocument);
	}

	@Test
	public void visitWithDevice_createsAndStoresAEvent() {
		eventVisitorImpl.visit(device, EVENT_MODE);

		verify(eventRepositoryImpl).save(deviceEventDocument);

	}

	@Test
	public void visitWithSensor_createsAndStoresAEvent() {
		eventVisitorImpl.visit(sensor, EVENT_MODE);

		verify(eventRepositoryImpl).save(sensorEventDocument);

	}

}
