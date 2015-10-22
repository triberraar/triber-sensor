package be.tribersoft.sensor.rest;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import be.tribersoft.sensor.domain.api.TypePatch;

public class TypePatchJson implements TypePatch {

	private String name;
	private Long version;

	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	@NotNull
	public Long getVersion() {
		return version;
	}

}
