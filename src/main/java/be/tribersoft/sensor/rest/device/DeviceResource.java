package be.tribersoft.sensor.rest.device;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.device.DeviceRepository;
import be.tribersoft.sensor.service.api.device.DeviceService;

@RestController
@RequestMapping("/device")
public class DeviceResource {

	@Inject
	private DeviceService deviceService;
	@Inject
	private DeviceRepository deviceRepository;
	@Inject
	private DeviceHateoasBuilder deviceHateoasBuilder;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<DeviceToJsonAdapter>> all() {
		return deviceHateoasBuilder.build(deviceRepository.all());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	public Resource<DeviceToJsonAdapter> get(@PathVariable("id") String id) {
		return deviceHateoasBuilder.build(deviceRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@Valid @RequestBody DevicePostJson devicePostJson) {
		deviceService.save(devicePostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public void update(@PathVariable("id") String id, @Valid @RequestBody DeviceUpdateJson device) {
		deviceService.update(id, device.getVersion(), device);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	public void delete(@PathVariable("id") String id, @Valid @RequestBody DeviceDeleteJson device) {
		deviceService.delete(id, device.getVersion());
	}

}
