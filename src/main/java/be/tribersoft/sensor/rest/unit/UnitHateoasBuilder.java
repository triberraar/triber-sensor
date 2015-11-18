package be.tribersoft.sensor.rest.unit;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.unit.Unit;

@Named
public class UnitHateoasBuilder {
	@Value("${api.version}")
	private String apiVersion;

	public Resource<UnitToJsonAdapter> build(Unit unit) {
		Resource<UnitToJsonAdapter> resource = new Resource<UnitToJsonAdapter>(new UnitToJsonAdapter(unit));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).get(apiVersion, unit.getId())).withSelfRel());
		return resource;
	}

	public Resources<Resource<UnitToJsonAdapter>> build(List<? extends Unit> units) {
		List<Resource<UnitToJsonAdapter>> transformedUnitResources = units.stream().map(unit -> {
			return build(unit);
		}).collect(Collectors.toList());

		Resources<Resource<UnitToJsonAdapter>> unitResources = new Resources<>(transformedUnitResources);
		unitResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).all(apiVersion)).withSelfRel());
		return unitResources;
	}
}
