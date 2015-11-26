package be.tribersoft.sensor.rest.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.event.Event;

@Named
public class EventHateoasBuilder {

	@Value("${api.version}")
	private String apiVersion;
	@Inject
	private EventUrlVisitorFactory eventUrlVisitorFactory;

	public PagedResources<Resource<EventToJsonAdapter>> build(Page<? extends Event> events, int page) {
		List<Resource<EventToJsonAdapter>> transformedEventResources = events.getContent().stream().map(event -> {
			Resource<EventToJsonAdapter> resource = new Resource<EventToJsonAdapter>(new EventToJsonAdapter(event));
			EventUrlVisitor visitor = eventUrlVisitorFactory.create();
			event.getEventSubject().accept(visitor, event);
			if (visitor.getLink().isPresent()) {
				resource.add(visitor.getLink().get());
			}
			return resource;
		}).collect(Collectors.toList());

		List<Link> links = new ArrayList<>();
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page)).withSelfRel());
		if (events.hasPrevious()) {
			links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page - 1)).withRel(Link.REL_PREVIOUS));
		}
		if (events.hasNext()) {
			links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page + 1)).withRel(Link.REL_NEXT));
		}
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, 0)).withRel(Link.REL_FIRST));
		int lastPage = events.getTotalPages() == 0 ? 0 : events.getTotalPages() - 1;
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, lastPage)).withRel(Link.REL_LAST));
		PageMetadata pageMetadata = new PageMetadata(events.getSize(), events.getNumber(), events.getTotalElements());
		PagedResources<Resource<EventToJsonAdapter>> result = new PagedResources<>(transformedEventResources, pageMetadata, links);
		return result;
	}
}
