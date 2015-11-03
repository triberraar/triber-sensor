package be.tribersoft.sensor.rest.sensor;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.rest.type.TypeResource;
import be.tribersoft.sensor.rest.unit.UnitResource;

@Named
public class SensorHateoasBuilder {

	public Resource<SensorToJsonAdapter> build(Sensor sensor) {
		Resource<SensorToJsonAdapter> resource = new Resource<SensorToJsonAdapter>(new SensorToJsonAdapter(sensor), ControllerLinkBuilder.linkTo(SensorResource.class).slash(sensor).withSelfRel());
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).get(sensor.getType().getId())).withRel("type"));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).get(sensor.getUnit().getId())).withRel("unit"));
		return resource;
	}

	public Resources<Resource<SensorToJsonAdapter>> build(List<? extends Sensor> sensors) {
		List<Resource<SensorToJsonAdapter>> transformedUnitResources = sensors.stream().map(sensor -> {
			return build(sensor);
		}).collect(Collectors.toList());

		Resources<Resource<SensorToJsonAdapter>> unitResources = new Resources<>(transformedUnitResources);
		unitResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).all()).withSelfRel());
		return unitResources;
	}
}
