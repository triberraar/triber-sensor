package be.tribersoft.sensor.rest.unit;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.unit.UnitRepository;
import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.unit.UnitService;

@RestController
@RequestMapping("/api/{apiVersion}/admin/unit")
public class UnitResource {

	@Inject
	private UnitService unitService;
	@Inject
	private UnitRepository unitRepository;
	@Inject
	private UnitHateoasBuilder unitHateoasBuilder;
	@Inject
	private VersionValidator versionValidator;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<UnitToJsonAdapter>> all(@PathVariable("apiVersion") String apiVersion) {
		versionValidator.validate(apiVersion);
		return unitHateoasBuilder.build(unitRepository.all());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	public Resource<UnitToJsonAdapter> get(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id) {
		versionValidator.validate(apiVersion);
		return unitHateoasBuilder.build(unitRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("apiVersion") String apiVersion, @Valid @RequestBody UnitPostJson unitPostJson) {
		versionValidator.validate(apiVersion);
		unitService.save(unitPostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public void update(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id, @Valid @RequestBody UnitUpdateJson unit) {
		versionValidator.validate(apiVersion);
		unitService.update(id, unit.getVersion(), unit);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	public void delete(@PathVariable("apiVersion") String apiVersion, @PathVariable("id") String id, @Valid @RequestBody UnitDeleteJson unit) {
		versionValidator.validate(apiVersion);
		unitService.delete(id, unit.getVersion());
	}

}
