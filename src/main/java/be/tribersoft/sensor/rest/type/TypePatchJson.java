package be.tribersoft.sensor.rest.type;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import be.tribersoft.sensor.domain.api.type.TypePatch;

public class TypePatchJson implements TypePatch {

	private String name;
	private Long version;

	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	@NotNull(message = "type.validation.error.version.null")
	public Long getVersion() {
		return version;
	}

}
