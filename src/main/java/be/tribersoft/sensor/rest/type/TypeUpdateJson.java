package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

public class TypeUpdateJson implements TypeMessage {

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

	@JsonProperty
	public void setVersion(Long version) {
		this.version = version;
	}

	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}
}
