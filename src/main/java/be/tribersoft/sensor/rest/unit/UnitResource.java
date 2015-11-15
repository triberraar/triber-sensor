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
import be.tribersoft.sensor.service.api.unit.UnitService;

@RestController
@RequestMapping("/api/admin/unit")
public class UnitResource {

	@Inject
	private UnitService unitService;
	@Inject
	private UnitRepository unitRepository;
	@Inject
	private UnitHateoasBuilder unitHateoasBuilder;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<UnitToJsonAdapter>> all() {
		return unitHateoasBuilder.build(unitRepository.all());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	public Resource<UnitToJsonAdapter> get(@PathVariable("id") String id) {
		return unitHateoasBuilder.build(unitRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@Valid @RequestBody UnitPostJson unitPostJson) {
		unitService.save(unitPostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public void update(@PathVariable("id") String id, @Valid @RequestBody UnitUpdateJson unit) {
		unitService.update(id, unit.getVersion(), unit);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	public void delete(@PathVariable("id") String id, @Valid @RequestBody UnitDeleteJson unit) {
		unitService.delete(id, unit.getVersion());
	}

}
