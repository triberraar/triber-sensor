package be.tribersoft.sensor.rest;

import javax.validation.constraints.NotNull;

public class TypeDeleteJson {

	private Long version;

	@NotNull
	public Long getVersion() {
		return version;
	}
}
