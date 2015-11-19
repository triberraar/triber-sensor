package be.tribersoft.sensor.rest.type;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.type.Type;

@Named
public class TypeHateoasBuilder {

	@Value("${api.version}")
	private String apiVersion;

	public Resource<TypeToJsonAdapter> build(Type type) {
		Resource<TypeToJsonAdapter> resource = new Resource<TypeToJsonAdapter>(new TypeToJsonAdapter(type));
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).get(apiVersion, type.getId())).withSelfRel());
		return resource;
	}

	public Resources<Resource<TypeToJsonAdapter>> build(List<? extends Type> types) {
		List<Resource<TypeToJsonAdapter>> transformedTypeResources = types.stream().map(type -> {
			return build(type);
		}).collect(Collectors.toList());

		Resources<Resource<TypeToJsonAdapter>> typeResources = new Resources<>(transformedTypeResources);
		typeResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TypeResource.class).all(apiVersion)).withSelfRel());
		return typeResources;
	}
}
