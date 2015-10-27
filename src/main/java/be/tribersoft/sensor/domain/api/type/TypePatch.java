package be.tribersoft.sensor.domain.api.type;

import java.util.Optional;

import javax.validation.constraints.Size;

public interface TypePatch {

	@Size(max = 255)
	Optional<String> getName();

}
