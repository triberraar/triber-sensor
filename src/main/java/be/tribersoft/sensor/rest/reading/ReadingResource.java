package be.tribersoft.sensor.rest.reading;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RestController
@RequestMapping("/api/device/{deviceId}/sensor/{sensorId}/reading")
public class ReadingResource {
	@Inject
	private ReadingRepository readingRepository;
	@Inject
	private ReadingHateoasBuilder readingHateoasBuilder;
	@Inject
	private ReadingService readingService;
	@Inject
	private SensorValidator sensorValidator;
	@Value("${rest.page.size}")
	private Integer pageSize;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resources<Resource<ReadingToJsonAdapter>> all(@PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId, @RequestParam(defaultValue = "0") int page) {
		sensorValidator.validate(deviceId, sensorId);
		Pageable pageable = new PageRequest(page, pageSize);
		return readingHateoasBuilder.build(deviceId, sensorId, readingRepository.allBySensor(sensorId, pageable), page);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId, @Valid @RequestBody ReadingPostJson readingPostJson) {
		sensorValidator.validate(deviceId, sensorId);
		readingService.save(sensorId, readingPostJson);
	}

}
