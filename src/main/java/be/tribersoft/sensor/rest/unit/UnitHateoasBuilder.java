package be.tribersoft.sensor.rest.unit;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.unit.Unit;

@Named
public class UnitHateoasBuilder {

	public Resource<UnitToJsonAdapter> build(Unit unit) {
		return new Resource<UnitToJsonAdapter>(new UnitToJsonAdapter(unit), ControllerLinkBuilder.linkTo(UnitResource.class).slash(unit.getId()).withSelfRel());
	}

	public Resources<Resource<UnitToJsonAdapter>> build(List<? extends Unit> units) {
		List<Resource<UnitToJsonAdapter>> transformedUnitResources = units.stream().map(unit -> {
			return new Resource<UnitToJsonAdapter>(new UnitToJsonAdapter(unit), ControllerLinkBuilder.linkTo(UnitResource.class).slash(unit.getId()).withSelfRel());
		}).collect(Collectors.toList());

		Resources<Resource<UnitToJsonAdapter>> unitResources = new Resources<>(transformedUnitResources);
		unitResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UnitResource.class).all()).withSelfRel());
		return unitResources;
	}
}
