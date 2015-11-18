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
import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.type.TypeService;

@RestController
@RequestMapping("/api/{apiVersion}/admin/type")
public class TypeResource {

	@Inject
	private TypeService typeService;
	@Inject
	private TypeRepository typeRepository;
	@Inject
	private TypeHateoasBuilder typeHateoasBuilder;
	@Inject
	private VersionValidator versionValidator;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<TypeToJsonAdapter>> all(@PathVariable("apiVersion") String apiVersion) {
		versionValidator.validate(apiVersion);
		return typeHateoasBuilder.build(typeRepository.all());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	public Resource<TypeToJsonAdapter> get(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id) {
		versionValidator.validate(apiVersion);
		return typeHateoasBuilder.build(typeRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("apiVersion") String apiVersion, @Valid @RequestBody TypePostJson typePostJson) {
		versionValidator.validate(apiVersion);
		typeService.save(typePostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public void update(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id, @Valid @RequestBody TypeUpdateJson type) {
		versionValidator.validate(apiVersion);
		typeService.update(id, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	public void delete(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id, @Valid @RequestBody TypeDeleteJson type) {
		versionValidator.validate(apiVersion);
		typeService.delete(id, type.getVersion());
	}

}
