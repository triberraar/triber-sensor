package be.tribersoft.sensor.rest.sensor;

import javax.inject.Inject;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;

@RestController
@RequestMapping("/api/sensor")
public class SensorResource {

	@Inject
	private SensorRepository sensorRepository;
	@Inject
	private SensorDeviceHateoasBuilder sensorHateoasBuilder;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<SensorToJsonAdapter>> all() {
		return sensorHateoasBuilder.build(sensorRepository.all());
	}

}
