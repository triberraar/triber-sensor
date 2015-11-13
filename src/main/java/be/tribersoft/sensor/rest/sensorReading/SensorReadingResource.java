package be.tribersoft.sensor.rest.sensorReading;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingRepository;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.sensorReading.SensorReadingService;

@RestController
@RequestMapping("/device/{deviceId}/sensor/{sensorId}/reading")
public class SensorReadingResource {
	@Inject
	private SensorReadingRepository sensorReadingRepository;
	@Inject
	private SensorReadingHateoasBuilder sensorReadingHateoasBuilder;
	@Inject
	private SensorReadingService sensorReadingService;
	@Inject
	private SensorValidator sensorValidator;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<SensorReadingToJsonAdapter>> all(@PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId) {
		sensorValidator.validate(deviceId, sensorId);
		return sensorReadingHateoasBuilder.build(sensorReadingRepository.allBySensor(sensorId));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId, @Valid @RequestBody SensorReadingPostJson sensorReadingPostJson) {
		sensorValidator.validate(deviceId, sensorId);
		sensorReadingService.save(sensorId, sensorReadingPostJson);
	}

}
