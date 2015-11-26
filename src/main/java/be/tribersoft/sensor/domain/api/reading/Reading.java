package be.tribersoft.sensor.domain.api.reading;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Identifiable;

import be.tribersoft.sensor.domain.api.event.Eventable;
import be.tribersoft.sensor.domain.api.sensor.Sensor;

public interface Reading extends Eventable, Identifiable<String> {
	@NotNull(message = "reading.validation.value.null")
	BigDecimal getValue();

	Long getVersion();

	@NotNull(message = "reading.validation.sensor.null")
	Sensor getSensor();

}
