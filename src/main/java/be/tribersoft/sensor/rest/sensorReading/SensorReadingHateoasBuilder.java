package be.tribersoft.sensor.rest.sensorReading;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;

@Named
public class SensorReadingHateoasBuilder {

	public Resources<Resource<SensorReadingToJsonAdapter>> build(List<? extends SensorReading> sensorReadings) {
		String deviceId = "";
		String sensorId = "";
		List<Resource<SensorReadingToJsonAdapter>> transformedSensorReadingResources = sensorReadings.stream().map(sensorReading -> {
			return new Resource<SensorReadingToJsonAdapter>(new SensorReadingToJsonAdapter(sensorReading));
		}).collect(Collectors.toList());
		if (!sensorReadings.isEmpty()) {
			deviceId = sensorReadings.get(0).getSensor().getDevice().getId();
			sensorId = sensorReadings.get(0).getSensor().getId();
		}

		Resources<Resource<SensorReadingToJsonAdapter>> sensorResources = new Resources<>(transformedSensorReadingResources);
		sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorReadingResource.class).all(deviceId, sensorId)).withSelfRel());
		return sensorResources;
	}
}
