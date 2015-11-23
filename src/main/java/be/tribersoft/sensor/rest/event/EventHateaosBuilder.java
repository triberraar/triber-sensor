package be.tribersoft.sensor.rest.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.event.Event;

@Named
public class EventHateaosBuilder {

	@Value("${api.version}")
	private String apiVersion;

	public Resources<Resource<EventToJsonAdapter>> build(Page<? extends Event> events, int page) {
		List<Resource<EventToJsonAdapter>> transformedEventResources = events.getContent().stream().map(event -> {
			Resource<EventToJsonAdapter> resource = new Resource<EventToJsonAdapter>(new EventToJsonAdapter(event));
			EventUrlVisitor visitor = new EventUrlVisitor();
			event.getEventSubject().accept(visitor, event);
			resource.add(visitor.getLink());
			return resource;
		}).collect(Collectors.toList());

		Resources<Resource<EventToJsonAdapter>> eventResources = new Resources<>(transformedEventResources);
		eventResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page)).withSelfRel());
		if (events.hasPrevious()) {
			eventResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page - 1)).withRel("previous"));
		}
		if (events.hasNext()) {
			eventResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(EventResource.class).all(apiVersion, page + 1)).withRel("next"));
		}
		return eventResources;
	}
}
