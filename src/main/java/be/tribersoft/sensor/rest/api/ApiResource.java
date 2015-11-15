package be.tribersoft.sensor.rest.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.common.rest.FutureApiVersionException;
import be.tribersoft.common.rest.IncorrectApiVersionException;
import be.tribersoft.semver.Version;

@RestController
@RequestMapping("/api")
public class ApiResource {

	@Value("${api.version}")
	private String apiVersion;

	@Inject
	private ApiHateoasBuilder apiHateoasBuilder;

	private Version semver;

	@PostConstruct
	public void init() {
		semver = new Version(apiVersion);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{apiVersion:.+}", produces = "application/json")
	public Resource<ApiToJsonAdapter> getWithVersion(@PathVariable("apiVersion") String apiVersion) {
		if (!semver.isCompatbile(new Version(apiVersion))) {
			throw new IncorrectApiVersionException(this.apiVersion, apiVersion);
		}
		if (semver.isBefore(new Version(apiVersion))) {
			throw new FutureApiVersionException(this.apiVersion, apiVersion);
		}
		return apiHateoasBuilder.build(apiVersion);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resource<ApiToJsonAdapter> get() {
		return apiHateoasBuilder.build();
	}

}
