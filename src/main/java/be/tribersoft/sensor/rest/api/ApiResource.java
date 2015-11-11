package be.tribersoft.sensor.rest.api;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.tribersoft.common.rest.IncorrectApiVersionException;

@RestController
@RequestMapping("/{apiVersion}")
public class ApiResource {

	@Value("${api.version}")
	private String apiVersion;

	@Inject
	private ApiHateoasBuilder apiHateoasBuilder;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resource<ApiToJsonAdapter> get(@PathVariable("apiVersion") String apiVersion) {
		if (!apiVersion.equals(this.apiVersion)) {
			throw new IncorrectApiVersionException(this.apiVersion, apiVersion);
		}
		return apiHateoasBuilder.build(apiVersion);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Resource<ApiToJsonAdapter> get() {
		return apiHateoasBuilder.build();
	}

}
