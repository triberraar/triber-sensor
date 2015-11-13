package be.tribersoft.sensor.domain.api.sensorReading;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Identifiable;

import be.tribersoft.sensor.domain.api.sensor.Sensor;

public interface SensorReading extends Identifiable<String> {
	@NotNull(message = "sensor.reading.validation.value.null")
	BigDecimal getValue();

	Long getVersion();

	@NotNull(message = "sensor.reading.validation.sensor.null")
	Sensor getSensor();

}
