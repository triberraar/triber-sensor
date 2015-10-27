package be.tribersoft.sensor.rest.type;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.type.Type;

@Named
public class TypeHateoasBuilder {

	public Resource<TypeToJsonAdapter> build(Type type) {
		return new Resource<TypeToJsonAdapter>(new TypeToJsonAdapter(type), ControllerLinkBuilder.linkTo(TypeResource.class).slash(type.getId()).withSelfRel());
	}

	public Resources<Resource<TypeToJsonAdapter>> build(List<? extends Type> types) {
		List<Resource<TypeToJsonAdapter>> transformedTypeResources = types.stream().map(type -> {
			return new Resource<TypeToJsonAdapter>(new TypeToJsonAdapter(type), ControllerLinkBuilder.linkTo(TypeResource.class).slash(type.getId()).withSelfRel());
		}).collect(Collectors.toList());

		Resources<Resource<TypeToJsonAdapter>> typeResources = new Resources<>(transformedTypeResources);
		typeResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).all()).withSelfRel());
		return typeResources;
	}
}
