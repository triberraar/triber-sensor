package be.tribersoft.sensor.domain.api.type;

import javax.validation.constraints.NotNull;

public interface TypeUpdate {

	@NotNull(message = "type.validation.error.name.null")
	String getName();

}
