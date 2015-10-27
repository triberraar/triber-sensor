package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeUpdate;

public class TypeUpdateJson implements TypeUpdate {

	private Long version;
	private String name;

	@NotNull(message = "type.validation.version.null")
	@JsonProperty
	public Long getVersion() {
		return version;
	}

	@Override
	@JsonProperty
	public String getName() {
		return name;
	}
}
