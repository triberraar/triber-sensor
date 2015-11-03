package be.tribersoft.sensor.domain.api.sensor;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface SensorMessage {

	@NotNull(message = "sensor.validation.name.null")
	@Size(max = 256, message = "sensor.validation.name.too.long")
	String getName();

	@Size(max = 4098, message = "sensor.validation.description.too.long")
	Optional<String> getDescription();

	@NotNull(message = "sensor.validation.type.null")
	String getTypeId();

	@NotNull(message = "sensor.validation.unit.null")
	String getUnitId();
}
