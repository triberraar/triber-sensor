package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

public class TypeUpdateJson implements TypeMessage {

	@NotNull(message = "type.validation.version.null")
	@JsonProperty
	private Long version;
	@JsonProperty
	private String name;

	public Long getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}

}
