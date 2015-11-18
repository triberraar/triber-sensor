package be.tribersoft.sensor.rest.reading;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.sensor.SensorResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class ReadingHateoasBuilder {

	@Inject
	private ReadingRepository readingRepository;
	@Value("${rest.page.size}")
	private int pageSize;
	@Value("${api.version}")
	private String apiVersion;

	public Resources<Resource<ReadingToJsonAdapter>> build(String deviceId, String sensorId, List<? extends Reading> readings, int page) {
		List<Resource<ReadingToJsonAdapter>> transformedReadingResources = readings.stream().map(reading -> {
			Resource<ReadingToJsonAdapter> resource = new Resource<ReadingToJsonAdapter>(new ReadingToJsonAdapter(reading));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(apiVersion, deviceId, sensorId)).withRel(SensorToJsonAdapter.SENSOR));
			return resource;
		}).collect(Collectors.toList());

		Resources<Resource<ReadingToJsonAdapter>> sensorResources = new Resources<>(transformedReadingResources);
		sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page)).withSelfRel());
		if (page > 0) {
			sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page - 1)).withRel("previous"));
		}
		if ((page + 1) * pageSize < readingRepository.countBySensor(sensorId)) {
			sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, deviceId, sensorId, page + 1)).withRel("next"));
		}
		return sensorResources;
	}
}
