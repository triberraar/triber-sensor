package be.tribersoft.sensor.domain.api.sensorReading;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public interface SensorReadingMessage {

	@NotNull(message = "sensor.reading.validation.value.null")
	BigDecimal getValue();

}
