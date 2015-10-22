package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

public interface TypeUpdate {

	@NotNull
	String getName();

}
