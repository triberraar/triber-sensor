package be.tribersoft.sensor.domain.api.sensor;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Identifiable;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.type.Type;
import be.tribersoft.sensor.domain.api.unit.Unit;

public interface Sensor extends Identifiable<String> {
	@NotNull(message = "sensor.validation.name.null")
	@Size(max = 256, message = "sensor.validation.name.too.long")
	String getName();

	Long getVersion();

	@Size(max = 4096, message = "sensor.validation.description.too.long")
	Optional<String> getDescription();

	@NotNull(message = "sensor.validation.type.null")
	Type getType();

	@NotNull(message = "sensor.validation.unit.null")
	Unit getUnit();

	@NotNull(message = "sensor.validation.device.null")
	Device getDevice();

}
