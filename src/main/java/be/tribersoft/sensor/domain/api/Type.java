package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

public interface Type {

	@NotNull(message = "type.validation.error.name.null")
	String getName();

	String getUuid();

	Long getVersion();
}
