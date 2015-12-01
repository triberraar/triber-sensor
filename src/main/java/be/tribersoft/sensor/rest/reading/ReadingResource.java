package be.tribersoft.sensor.rest.reading;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RestController
@RequestMapping("/api/{apiVersion}/device/{deviceId}/sensor/{sensorId}/reading")
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
	@Inject
	private VersionValidator versionValidator;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public PagedResources<Resource<ReadingToJsonAdapter>> all(@PathVariable("apiVersion") String apiVersion, @PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId, @RequestParam(defaultValue = "0") int page) {
		versionValidator.validate(apiVersion);
		sensorValidator.validate(deviceId, sensorId);
		Pageable pageable = new PageRequest(page, pageSize);
		return readingHateoasBuilder.build(deviceId, sensorId, readingRepository.allBySensor(sensorId, pageable), page);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void save(@PathVariable("apiVersion") String apiVersion, @PathVariable("deviceId") String deviceId, @PathVariable("sensorId") String sensorId, @Valid @RequestBody ReadingPostJson readingPostJson) {
		versionValidator.validate(apiVersion);
		sensorValidator.validate(deviceId, sensorId);
		readingService.save(sensorId, readingPostJson);
	}

}
