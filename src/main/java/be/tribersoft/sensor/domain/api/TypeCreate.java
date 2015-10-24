package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

public interface TypeCreate {

	@NotNull
	public String getName();

}
