package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeDeleteJson {

	private Long version;

	@NotNull(message = "type.validation.version.null")
	@JsonProperty
	public Long getVersion() {
		return version;
	}

	@JsonProperty
	public void setVersion(Long version) {
		this.version = version;
	}
}
