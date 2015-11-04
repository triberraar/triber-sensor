package be.tribersoft.sensor.domain.api.device;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Identifiable;

public interface Device extends Identifiable<String> {

	@Size(max = 256, message = "device.validation.location.too.long")
	Optional<String> getLocation();

	@Size(max = 4096, message = "device.validation.description.too.long")
	Optional<String> getDescription();

	Long getVersion();

	String getId();

	@NotNull(message = "device.validation.name.null")
	@Size(max = 256, message = "device.validation.name.too.long")
	String getName();

}
