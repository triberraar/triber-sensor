package be.tribersoft.sensor.rest.api;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.sensor.rest.VersionValidator;

@RestController
@RequestMapping("/api")
public class ApiResource {

	@Value("${api.version}")
	private String apiVersion;

	@Inject
	private ApiHateoasBuilder apiHateoasBuilder;

	@Inject
	private VersionValidator versionValidator;

	@RequestMapping(method = RequestMethod.GET, value = "/{apiVersion:.+}", produces = "application/json")
	public Resource<ApiToJsonAdapter> getWithVersion(@PathVariable("apiVersion") String apiVersion) {
		versionValidator.validate(apiVersion);
		return apiHateoasBuilder.build();
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resource<ApiToJsonAdapter> get() {
		return apiHateoasBuilder.build();
	}

}
