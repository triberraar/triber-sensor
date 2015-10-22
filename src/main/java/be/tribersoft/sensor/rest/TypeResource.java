package be.tribersoft.sensor.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.TypeEntity;
import be.tribersoft.sensor.domain.api.Type;
import be.tribersoft.sensor.domain.api.TypeRepository;
import be.tribersoft.sensor.service.api.TypeService;

@RestController
@RequestMapping("/type")
public class TypeResource {

	@Inject
	private TypeService typeService;
	@Inject
	private TypeRepository typeRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<? extends Type> all() {
		return typeRepository.all();
	}

	@RequestMapping(method = RequestMethod.POST)
	public void save(@Valid @RequestBody TypeEntity type) {
		typeService.save(type);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public Type get(@PathVariable("uuid") String uuid) {
		return typeRepository.getByUuid(uuid);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
	public void update(@PathVariable("uuid") String uuid, @Valid @RequestBody TypeUpdateJson type) {
		typeService.update(uuid, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{uuid}")
	public void patch(@PathVariable("uuid") String uuid, @Valid @RequestBody TypePatchJson type) {
		typeService.patch(uuid, type.getVersion(), type);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{uuid}")
	public void delete(@PathVariable("uuid") String uuid, @Valid @RequestBody TypeDeleteJson type) {
		typeService.delete(uuid, type.getVersion());
	}

}
