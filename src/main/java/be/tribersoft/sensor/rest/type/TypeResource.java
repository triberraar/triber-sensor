package be.tribersoft.sensor.rest.type;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.type.TypeRepository;
import be.tribersoft.sensor.service.api.type.TypeService;

@RestController
@RequestMapping("/type")
public class TypeResource {

	@Inject
	private TypeService typeService;
	@Inject
	private TypeRepository typeRepository;
	@Inject
	private TypeHateoasBuilder typeHateoasBuilder;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<TypeToJsonAdapter>> all() {
		return typeHateoasBuilder.build(typeRepository.all());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Resource<TypeToJsonAdapter> get(@PathVariable("id") String id) {
		return typeHateoasBuilder.build(typeRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	public void save(@Valid @RequestBody TypePostJson typePostJson) {
		typeService.save(typePostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public void update(@PathVariable("id") String id, @Valid @RequestBody TypeUpdateJson type) {
		typeService.update(id, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void delete(@PathVariable("id") String id, @Valid @RequestBody TypeDeleteJson type) {
		typeService.delete(id, type.getVersion());
	}

}
