package be.tribersoft.sensor.rest.sensorReading;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;

public class SensorReadingPostJson implements SensorReadingMessage {

	@JsonProperty
	private BigDecimal value;

	@Override
	public BigDecimal getValue() {
		return value;
	}

}
