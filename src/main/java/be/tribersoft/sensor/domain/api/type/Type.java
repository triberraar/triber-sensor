package be.tribersoft.sensor.domain.api.type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Identifiable;

public interface Type extends Identifiable<String> {

	@NotNull(message = "type.validation.name.null")
	@Size(max = 255, message = "type.validation.name.too.long")
	String getName();

	Long getVersion();

}
