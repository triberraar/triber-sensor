package be.tribersoft.sensor.rest;

import javax.validation.constraints.NotNull;

import be.tribersoft.sensor.domain.api.TypeUpdate;

public class TypeUpdateJson implements TypeUpdate {

	private Long version;
	private String name;

	@NotNull
	public Long getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}
}
