package be.tribersoft.sensor.rest.type;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypePatch;

public class TypePatchJson implements TypePatch {

	private String name;
	private Long version;

	@Override
	@JsonProperty
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	@NotNull(message = "type.validation.version.null")
	@JsonProperty
	public Long getVersion() {
		return version;
	}

	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public void setVersion(Long version) {
		this.version = version;
	}

}
