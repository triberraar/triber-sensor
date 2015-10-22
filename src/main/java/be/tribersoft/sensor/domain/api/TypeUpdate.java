package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

public interface TypeUpdate {

	@NotNull(message = "type.validation.error.name.null")
	String getName();

}
