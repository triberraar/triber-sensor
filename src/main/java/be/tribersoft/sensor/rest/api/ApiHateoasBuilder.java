package be.tribersoft.sensor.rest.api;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${api.version}")
	private String apiVersion;

	public Resource<ApiToJsonAdapter> build() {
		Resource<ApiToJsonAdapter> resource = new Resource<ApiToJsonAdapter>(new ApiToJsonAdapter(apiVersion));

		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ApiResource.class).getWithVersion(apiVersion)).withSelfRel());
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).all(apiVersion)).withRel(TypeToJsonAdapter.TYPES));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).all(apiVersion)).withRel(UnitToJsonAdapter.UNITS));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DeviceResource.class).all(apiVersion)).withRel(DeviceToJsonAdapter.DEVICES));
		return resource;
	}

}
