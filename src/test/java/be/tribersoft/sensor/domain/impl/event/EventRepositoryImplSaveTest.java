package be.tribersoft.sensor.domain.impl.event;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryImplSaveTest {

	@InjectMocks
	private EventRepositoryImpl eventRepositoryImpl;

	@Mock
	private EventJpaRepository eventJpaRepository;

	@Mock
	private EventDocument eventDocument;

	@Test
	public void delegatesToJpaRepository() {
		eventRepositoryImpl.save(eventDocument);

		verify(eventJpaRepository).save(eventDocument);
	}
}
