package be.tribersoft.sensor.rest.sensor;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.rest.device.DeviceResource;
import be.tribersoft.sensor.rest.device.DeviceToJsonAdapter;
import be.tribersoft.sensor.rest.reading.ReadingResource;
import be.tribersoft.sensor.rest.reading.ReadingToJsonAdapter;
import be.tribersoft.sensor.rest.type.TypeResource;
import be.tribersoft.sensor.rest.type.TypeToJsonAdapter;
import be.tribersoft.sensor.rest.unit.UnitResource;
import be.tribersoft.sensor.rest.unit.UnitToJsonAdapter;

@Named
public class SensorHateoasBuilder {

	@Value("${api.version}")
	private String apiVersion;

	public Resource<SensorToJsonAdapter> build(Sensor sensor) {
		Resource<SensorToJsonAdapter> resource = new Resource<SensorToJsonAdapter>(new SensorToJsonAdapter(sensor));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(apiVersion, sensor.getDevice().getId(), sensor.getId())).withSelfRel());
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).get(apiVersion, sensor.getType().getId())).withRel(TypeToJsonAdapter.TYPE));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).get(apiVersion, sensor.getUnit().getId())).withRel(UnitToJsonAdapter.UNIT));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DeviceResource.class).get(apiVersion, sensor.getDevice().getId())).withRel(DeviceToJsonAdapter.DEVICE));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, sensor.getDevice().getId(), sensor.getId(), 0)).withRel(ReadingToJsonAdapter.READINGS));
		return resource;
	}

	public Resources<Resource<SensorToJsonAdapter>> build(String deviceId, List<? extends Sensor> sensors) {
		List<Resource<SensorToJsonAdapter>> transformedSensorResources = sensors.stream().map(sensor -> {
			return build(sensor);
		}).collect(Collectors.toList());

		Resources<Resource<SensorToJsonAdapter>> sensorResources = new Resources<>(transformedSensorResources);
		sensorResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).all(apiVersion, deviceId)).withSelfRel());
		return sensorResources;
	}
}
