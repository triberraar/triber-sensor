package be.tribersoft.sensor.rest.type;

import javax.validation.constraints.NotNull;

import be.tribersoft.sensor.domain.api.type.TypeUpdate;

public class TypeUpdateJson implements TypeUpdate {

	private Long version;
	private String name;

	@NotNull(message = "type.validation.version.null")
	public Long getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}
}
