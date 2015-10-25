package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

public class TypeDeleteJson {

	private Long version;

	@NotNull(message = "type.validation.version.null")
	public Long getVersion() {
		return version;
	}
}
