package be.tribersoft.sensor.rest.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.event.Event;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventHateoasBuilderBuildTest {
	private static final EventSubject EVENT_SUBJECT_1 = EventSubject.READING;
	private static final EventMode EVENT_MODE_1 = EventMode.DELETED;
	private static final String EVENT_ID_1 = "event id 1";
	private static final Date CREATION_DATE_1 = new Date(12);
	private static final EventSubject EVENT_SUBJECT_2 = EventSubject.DEVICE;
	private static final EventMode EVENT_MODE_2 = EventMode.UPDATED;
	private static final String EVENT_ID_2 = "event id 2";
	private static final Date CREATION_DATE_2 = new Date(212);
	private static final int SIZE = 42;
	private static final int TOTAL_PAGES = 22;
	private static final long TOTAL_ELEMENTS = 122L;
	private static final int NUMBER = 2;
	private static final String API_VERSION = "apiVersion";
	private static int PAGE = 0;

	protected MockHttpServletRequest request;

	@InjectMocks
	private EventHateoasBuilder builder;

	@Mock
	private Event event1, event2;
	@Mock
	private Page<? extends Event> page;
	@Mock
	private EventUrlVisitorFactory eventUrlVisitorFactory;
	@Mock
	private EventUrlVisitor eventUrlVisitor;
	@Mock
	private Link link;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		when(eventUrlVisitorFactory.create()).thenReturn(eventUrlVisitor);
		when(eventUrlVisitor.getLink()).thenReturn(Optional.empty());
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		doReturn(Arrays.asList(event1, event2)).when(page).getContent();
		when(page.getNumber()).thenReturn(NUMBER);
		when(page.getTotalPages()).thenReturn(TOTAL_PAGES);
		when(page.getSize()).thenReturn(SIZE);
		when(page.getTotalElements()).thenReturn(TOTAL_ELEMENTS);

		when(event1.getCreationDate()).thenReturn(CREATION_DATE_1);
		when(event1.getEventId()).thenReturn(EVENT_ID_1);
		when(event1.getEventMode()).thenReturn(EVENT_MODE_1);
		when(event1.getEventSubject()).thenReturn(EVENT_SUBJECT_1);
		when(event2.getCreationDate()).thenReturn(CREATION_DATE_2);
		when(event2.getEventId()).thenReturn(EVENT_ID_2);
		when(event2.getEventMode()).thenReturn(EVENT_MODE_2);
		when(event2.getEventSubject()).thenReturn(EVENT_SUBJECT_2);
		Whitebox.setInternalState(builder, API_VERSION, API_VERSION);
	}

	@Test
	public void buildsLinksAndMetaDataForEvents() {
		PagedResources<Resource<EventToJsonAdapter>> eventResources = builder.build(page, PAGE);

		List<Link> links = eventResources.getLinks();
		assertThat(links.size()).isEqualTo(3);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=21");

		assertThat(eventResources.getContent().size()).isEqualTo(2);
		Collection<Resource<EventToJsonAdapter>> content = eventResources.getContent();
		Iterator<Resource<EventToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<EventToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getEventId()).isEqualTo(EVENT_ID_1);
		assertThat(first.getContent().getCreationDate()).isEqualTo(CREATION_DATE_1);
		assertThat(first.getContent().getEventMode()).isEqualTo(EVENT_MODE_1.getMessage());
		assertThat(first.getContent().getEventSubject()).isEqualTo(EVENT_SUBJECT_1.getMessage());
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(0);
		Resource<EventToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getEventId()).isEqualTo(EVENT_ID_2);
		assertThat(second.getContent().getCreationDate()).isEqualTo(CREATION_DATE_2);
		assertThat(second.getContent().getEventMode()).isEqualTo(EVENT_MODE_2.getMessage());
		assertThat(second.getContent().getEventSubject()).isEqualTo(EVENT_SUBJECT_2.getMessage());
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(0);

		assertThat(eventResources.getMetadata().getNumber()).isEqualTo(NUMBER);
		assertThat(eventResources.getMetadata().getSize()).isEqualTo(SIZE);
		assertThat(eventResources.getMetadata().getTotalElements()).isEqualTo(TOTAL_ELEMENTS);
		assertThat(eventResources.getMetadata().getTotalPages()).isEqualTo(3L);
	}

	@Test
	public void addsPreviousLinkWhenNotOnFirstPage() {
		when(page.hasPrevious()).thenReturn(true);
		Resources<Resource<EventToJsonAdapter>> eventResources = builder.build(page, 1);

		List<Link> links = eventResources.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=1");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_PREVIOUS);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=21");

	}

	@Test
	public void addsNextLinkWhenMoreItemsExist() {
		when(page.hasNext()).thenReturn(true);
		Resources<Resource<EventToJsonAdapter>> eventResources = builder.build(page, 0);

		List<Link> links = eventResources.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_NEXT);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=1");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=21");

	}

	@Test
	public void addsNextLinkWhenMoreItemsExistAndPreviousWhenNotOnFirstPage() {
		when(page.hasNext()).thenReturn(true);
		when(page.hasPrevious()).thenReturn(true);
		Resources<Resource<EventToJsonAdapter>> eventResources = builder.build(page, 1);

		List<Link> links = eventResources.getLinks();
		assertThat(links.size()).isEqualTo(5);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=1");
		assertThat(links.get(1).getRel()).isEqualTo(Link.REL_PREVIOUS);
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(2).getRel()).isEqualTo(Link.REL_NEXT);
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=2");
		assertThat(links.get(3).getRel()).isEqualTo(Link.REL_FIRST);
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=0");
		assertThat(links.get(4).getRel()).isEqualTo(Link.REL_LAST);
		assertThat(links.get(4).getHref()).isEqualTo("http://localhost/api/" + API_VERSION + "/event?page=21");

	}

	@Test
	public void addsDetailLinkWhenALinkExists() {
		when(eventUrlVisitor.getLink()).thenReturn(Optional.of(link));

		PagedResources<Resource<EventToJsonAdapter>> eventResources = builder.build(page, PAGE);
		assertThat(eventResources.getContent().size()).isEqualTo(2);
		Collection<Resource<EventToJsonAdapter>> content = eventResources.getContent();
		Iterator<Resource<EventToJsonAdapter>> iterator = content.iterator();

		assertThat(content.size()).isEqualTo(2);
		Resource<EventToJsonAdapter> first = iterator.next();
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0)).isEqualTo(link);
		Resource<EventToJsonAdapter> second = iterator.next();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0)).isEqualTo(link);
	}
}
