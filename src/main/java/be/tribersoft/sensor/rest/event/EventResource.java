package be.tribersoft.sensor.rest.event;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.event.EventRepository;
import be.tribersoft.sensor.rest.VersionValidator;

@RestController
@RequestMapping("/api/{apiVersion}/event")
public class EventResource {

	@Value("${rest.page.size}")
	private Integer pageSize;
	@Inject
	private VersionValidator versionValidator;
	@Inject
	private EventHateaosBuilder eventHateaosBuilder;
	@Inject
	private EventRepository eventRepository;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<EventToJsonAdapter>> all(@PathVariable("apiVersion") String apiVersion, @RequestParam(defaultValue = "0") int page) {
		versionValidator.validate(apiVersion);
		Pageable pageable = new PageRequest(page, pageSize);
		return eventHateaosBuilder.build(eventRepository.all(pageable), page);
	}
}
