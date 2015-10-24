package be.tribersoft.sensor.rest.type;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.type.Type;
import be.tribersoft.sensor.domain.api.type.TypeRepository;
import be.tribersoft.sensor.service.api.type.TypeService;

@RestController
@RequestMapping("/type")
public class TypeResource {

	@Inject
	private TypeService typeService;
	@Inject
	private TypeRepository typeRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<TypeToJsonAdapter>> all() {
		List<Resource<TypeToJsonAdapter>> typeResource = typeRepository.all().stream().map(type -> {
			return new Resource<TypeToJsonAdapter>(new TypeToJsonAdapter(type), ControllerLinkBuilder.linkTo(this.getClass()).slash(type.getId()).withSelfRel());
		}).collect(Collectors.toList());

		Resources<Resource<TypeToJsonAdapter>> typeResources = new Resources<>(typeResource);
		typeResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).all()).withSelfRel());
		return typeResources;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Resource<TypeToJsonAdapter> get(@PathVariable("id") String id) {
		Type type = typeRepository.getById(id);
		Resource<TypeToJsonAdapter> typeResource = new Resource<TypeToJsonAdapter>(new TypeToJsonAdapter(type), ControllerLinkBuilder.linkTo(this.getClass()).slash(type.getId()).withSelfRel());
		return typeResource;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void save(@Valid @RequestBody TypePostJson typePostJson) {
		typeService.save(typePostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public void update(@PathVariable("id") String id, @Valid @RequestBody TypeUpdateJson type) {
		typeService.update(id, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public void patch(@PathVariable("id") String id, @Valid @RequestBody TypePatchJson type) {
		typeService.patch(id, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void delete(@PathVariable("id") String id, @Valid @RequestBody TypeDeleteJson type) {
		typeService.delete(id, type.getVersion());
	}

}
