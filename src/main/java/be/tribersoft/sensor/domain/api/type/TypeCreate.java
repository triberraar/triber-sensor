package be.tribersoft.sensor.domain.api.type;

import javax.validation.constraints.NotNull;

public interface TypeCreate {

	@NotNull
	public String getName();

}
