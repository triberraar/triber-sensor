package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

public interface Type {

	@NotNull
	String getName();

	String getUuid();

	Long getVersion();
}
