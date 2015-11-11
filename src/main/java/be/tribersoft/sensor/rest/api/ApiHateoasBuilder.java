package be.tribersoft.sensor.rest.api;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.rest.device.DeviceResource;
import be.tribersoft.sensor.rest.device.DeviceToJsonAdapter;
import be.tribersoft.sensor.rest.type.TypeResource;
import be.tribersoft.sensor.rest.type.TypeToJsonAdapter;
import be.tribersoft.sensor.rest.unit.UnitResource;
import be.tribersoft.sensor.rest.unit.UnitToJsonAdapter;

@Named
public class ApiHateoasBuilder {
	public Resource<ApiToJsonAdapter> build(String apiVersion) {
		Resource<ApiToJsonAdapter> resource = new Resource<ApiToJsonAdapter>(new ApiToJsonAdapter(apiVersion));

		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ApiResource.class).get(apiVersion)).withSelfRel());
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).all()).withRel(TypeToJsonAdapter.TYPES));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).all()).withRel(UnitToJsonAdapter.UNITS));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DeviceResource.class).all()).withRel(DeviceToJsonAdapter.DEVICES));
		return resource;
	}
}
