package be.tribersoft.sensor.rest.reading;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.rest.sensor.SensorResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class ReadingHateoasBuilder {

	@Value("${api.version}")
	private String apiVersion;

	public Resources<Resource<ReadingToJsonAdapter>> build(String deviceId, String sensorId, Page<? extends Reading> readings, int page) {
		List<Resource<ReadingToJsonAdapter>> transformedReadingResources = readings.getContent().stream().map(reading -> {
			Resource<ReadingToJsonAdapter> resource = new Resource<ReadingToJsonAdapter>(new ReadingToJsonAdapter(reading));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(apiVersion, deviceId, sensorId)).withRel(SensorToJsonAdapter.SENSOR));
			return resource;
		}).collect(Collectors.toList());

		Resources<Resource<ReadingToJsonAdapter>> readingResources = new Resources<>(transformedReadingResources);
		readingResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page)).withSelfRel());
		if (readings.hasPrevious()) {
			readingResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page - 1)).withRel("previous"));
		}
		if (readings.hasNext()) {
			readingResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page + 1)).withRel("next"));
		}
		return readingResources;
	}
}
