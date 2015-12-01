package be.tribersoft.sensor.domain.api.reading;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public interface ReadingMessage {

	@NotNull(message = "reading.validation.value.null")
	BigDecimal getValue();

}
