package be.tribersoft.sensor.domain.api.type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface TypeMessage {

	@NotNull(message = "type.validation.name.null")
	@Size(max = 255, message = "type.validation.name.too.long")
	String getName();

}
