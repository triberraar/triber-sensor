package be.tribersoft.sensor.rest.sensor;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@RestController
@RequestMapping("/api/device/{deviceId}/sensor")
public class SensorResource {
	@Inject
	private SensorRepository sensorRepository;
	@Inject
	private SensorHateoasBuilder sensorHateoasBuilder;
	@Inject
	private SensorService sensorService;
	@Inject
	private SensorValidator sensorValidator;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<SensorToJsonAdapter>> all(@PathVariable("deviceId") String deviceId) {
		return sensorHateoasBuilder.build(deviceId, sensorRepository.allByDevice(deviceId));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	public Resource<SensorToJsonAdapter> get(@PathVariable("deviceId") String deviceId, @PathVariable("id") String id) {
		sensorValidator.validate(deviceId, id);
		return sensorHateoasBuilder.build(sensorRepository.getById(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("deviceId") String deviceId, @Valid @RequestBody SensorPostJson sensorPostJson) {
		sensorService.save(deviceId, sensorPostJson);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public void update(@PathVariable("deviceId") String deviceId, @PathVariable("id") String id, @Valid @RequestBody SensorUpdateJson sensor) {
		sensorValidator.validate(deviceId, id);
		sensorService.update(id, sensor.getVersion(), sensor);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	public void delete(@PathVariable("deviceId") String deviceId, @PathVariable("id") String id, @Valid @RequestBody SensorDeleteJson sensor) {
		sensorValidator.validate(deviceId, id);
		sensorService.delete(id, sensor.getVersion());
	}
}
