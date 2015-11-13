package be.tribersoft.sensor.rest.reading;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.rest.sensor.SensorDeviceResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class ReadingHateoasBuilder {

	public Resources<Resource<ReadingToJsonAdapter>> build(List<? extends Reading> readings) {
		String deviceId = "";
		String sensorId = "";
		List<Resource<ReadingToJsonAdapter>> transformedReadingResources = readings.stream().map(reading -> {
			Resource<ReadingToJsonAdapter> resource = new Resource<ReadingToJsonAdapter>(new ReadingToJsonAdapter(reading));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorDeviceResource.class).get(reading.getSensor().getDevice().getId(), reading.getSensor().getId())).withRel(SensorToJsonAdapter.SENSOR));
			return resource;
		}).collect(Collectors.toList());
		if (!readings.isEmpty()) {
			deviceId = readings.get(0).getSensor().getDevice().getId();
			sensorId = readings.get(0).getSensor().getId();
		}

		Resources<Resource<ReadingToJsonAdapter>> sensorResources = new Resources<>(transformedReadingResources);
		sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(deviceId, sensorId)).withSelfRel());
		return sensorResources;
	}
}
