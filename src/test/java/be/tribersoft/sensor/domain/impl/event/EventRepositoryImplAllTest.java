package be.tribersoft.sensor.domain.impl.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryImplAllTest {

	@InjectMocks
	private EventRepositoryImpl eventRepositoryImpl;

	@Mock
	private EventJpaRepository eventJpaRepository;

	@Mock
	private EventDocument eventDocument1, eventDocument2;

	@Before
	public void setUp() {
		when(eventJpaRepository.findAllByOrderByCreationDateDesc()).thenReturn(Arrays.asList(eventDocument1, eventDocument2));
	}

	@Test
	public void delegatesToJpaRepository() {
		List<EventDocument> result = eventRepositoryImpl.all();

		assertThat(result).isEqualTo(Arrays.asList(eventDocument1, eventDocument2));
	}
}
