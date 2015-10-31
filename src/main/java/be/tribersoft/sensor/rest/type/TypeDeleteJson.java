package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeDeleteJson {

	@JsonProperty
	@NotNull(message = "type.validation.version.null")
	private Long version;

	public Long getVersion() {
		return version;
	}

}
