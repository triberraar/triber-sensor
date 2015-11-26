package be.tribersoft.sensor.rest.reading;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.rest.sensor.SensorResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class ReadingHateoasBuilder {

	@Value("${api.version}")
	private String apiVersion;

	public PagedResources<Resource<ReadingToJsonAdapter>> build(String deviceId, String sensorId, Page<? extends Reading> readings, int page) {
		List<Resource<ReadingToJsonAdapter>> transformedReadingResources = readings.getContent().stream().map(reading -> {
			Resource<ReadingToJsonAdapter> resource = new Resource<ReadingToJsonAdapter>(new ReadingToJsonAdapter(reading));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(apiVersion, deviceId, sensorId)).withRel(SensorToJsonAdapter.SENSOR));
			return resource;
		}).collect(Collectors.toList());

		List<Link> links = new ArrayList<>();
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page)).withSelfRel());
		if (readings.hasPrevious()) {
			links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page - 1)).withRel(Link.REL_PREVIOUS));
		}
		if (readings.hasNext()) {
			links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page + 1)).withRel(Link.REL_NEXT));
		}
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, 0)).withRel(Link.REL_FIRST));
		int lastPage = readings.getTotalPages() == 0 ? 0 : readings.getTotalPages() - 1;
		links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, lastPage)).withRel(Link.REL_LAST));
		PageMetadata pageMetadata = new PageMetadata(readings.getSize(), readings.getNumber(), readings.getTotalElements());
		PagedResources<Resource<ReadingToJsonAdapter>> result = new PagedResources<>(transformedReadingResources, pageMetadata, links);
		return result;
	}
}
