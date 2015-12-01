package be.tribersoft.sensor.domain.impl.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryImplAllTest {

	private static final int PAGE_NUMBER = 2;
	private static final int PAGE_SIZE = 8;

	@InjectMocks
	private EventRepositoryImpl eventRepositoryImpl;

	@Mock
	private EventJpaRepository eventJpaRepository;

	@Mock
	private EventDocument eventDocument1, eventDocument2;

	@Mock
	private Pageable pageable;

	@Mock
	private Page<EventDocument> page;
	@Captor
	private ArgumentCaptor<PageRequest> pageRequestCaptor;

	@Before
	public void setUp() {
		when(eventJpaRepository.findAll(pageRequestCaptor.capture())).thenReturn(page);
		when(pageable.getPageSize()).thenReturn(PAGE_SIZE);
		when(pageable.getPageNumber()).thenReturn(PAGE_NUMBER);
	}

	@Test
	public void delegatesToJpaRepository() {
		Page<EventDocument> result = eventRepositoryImpl.all(pageable);

		assertThat(result).isEqualTo(page);
		PageRequest pageRequest = pageRequestCaptor.getValue();
		assertThat(pageRequest.getPageNumber()).isEqualTo(PAGE_NUMBER);
		assertThat(pageRequest.getPageSize()).isEqualTo(PAGE_SIZE);
		assertThat(pageRequest.getSort().getOrderFor("creationDate").getDirection()).isEqualTo(Direction.DESC);
		assertThat(pageRequest.getSort().getOrderFor("creationDate").getProperty()).isEqualTo("creationDate");
	}
}
