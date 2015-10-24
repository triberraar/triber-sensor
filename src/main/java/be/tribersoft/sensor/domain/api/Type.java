package be.tribersoft.sensor.domain.api;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Identifiable;

public interface Type extends Identifiable<String> {

	@NotNull(message = "type.validation.error.name.null")
	String getName();

	Long getVersion();
}
