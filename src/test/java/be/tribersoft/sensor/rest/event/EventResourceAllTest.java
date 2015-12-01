package be.tribersoft.sensor.rest.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.domain.api.event.Event;
import be.tribersoft.sensor.domain.api.event.EventRepository;
import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class EventResourceAllTest {
	private static final String API_VERSION = "apiVersion";
	private static final int PAGE_SIZE = 10;
	private static final int PAGE = 2;

	@InjectMocks
	private EventResource eventResource;

	@Mock
	private EventRepository eventRepository;
	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;
	@Mock
	private VersionValidator versionValidator;
	@Mock
	private Page<? extends Event> page;
	@Mock
	private PagedResources<Resource<EventToJsonAdapter>> pagedResources;
	@Mock
	private EventHateoasBuilder eventHateaosBuilder;

	@Before
	public void setUp() {
		doReturn(page).when(eventRepository).all(pageableCaptor.capture());
		when(eventHateaosBuilder.build(page, PAGE)).thenReturn(pagedResources);
		Whitebox.setInternalState(eventResource, "pageSize", PAGE_SIZE);
	}

	@Test
	public void delegatesToService() {
		PagedResources<Resource<EventToJsonAdapter>> result = eventResource.all(API_VERSION, PAGE);

		assertThat(result).isSameAs(pagedResources);
		Pageable pageable = pageableCaptor.getValue();
		assertThat(pageable.getPageNumber()).isEqualTo(PAGE);
		assertThat(pageable.getPageSize()).isEqualTo(PAGE_SIZE);
		verify(versionValidator).validate(API_VERSION);
	}
}
