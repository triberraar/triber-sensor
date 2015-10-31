package be.tribersoft.sensor.domain.api.unit;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface UnitMessage {

	@NotNull(message = "unit.validation.name.null")
	@Size(max = 255, message = "unit.validation.name.too.long")
	public String getName();

	@Size(max = 128, message = "unit.validation.symbol.too.long")
	public Optional<String> getSymbol();

}
