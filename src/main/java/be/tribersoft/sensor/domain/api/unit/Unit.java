package be.tribersoft.sensor.domain.api.unit;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Identifiable;

public interface Unit extends Identifiable<String> {

	Long getVersion();

	@NotNull(message = "unit.validation.name.null")
	@Size(max = 256, message = "unit.validation.name.too.long")
	String getName();

	@Size(max = 128, message = "unit.validation.symbol.too.long")
	Optional<String> getSymbol();

}
