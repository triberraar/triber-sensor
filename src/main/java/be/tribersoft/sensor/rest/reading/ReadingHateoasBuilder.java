package be.tribersoft.sensor.rest.reading;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.rest.sensor.SensorResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class ReadingHateoasBuilder {

	public Resources<Resource<ReadingToJsonAdapter>> build(String deviceId, String sensorId, List<? extends Reading> readings) {
		List<Resource<ReadingToJsonAdapter>> transformedReadingResources = readings.stream().map(reading -> {
			Resource<ReadingToJsonAdapter> resource = new Resource<ReadingToJsonAdapter>(new ReadingToJsonAdapter(reading));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(deviceId, sensorId)).withRel(SensorToJsonAdapter.SENSOR));
			return resource;
		}).collect(Collectors.toList());

		Resources<Resource<ReadingToJsonAdapter>> sensorResources = new Resources<>(transformedReadingResources);
		sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(deviceId, sensorId)).withSelfRel());
		return sensorResources;
	}
}
