package be.tribersoft.sensor.domain.api.type;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface DeviceMessage {

	@NotNull(message = "device.validation.name.null")
	@Size(max = 256, message = "device.validation.name.too.long")
	String getName();

	@Size(max = 4098, message = "device.validation.description.too.long")
	Optional<String> getDescription();

	@Size(max = 256, message = "device.validation.location.too.long")
	Optional<String> getLocation();
}
