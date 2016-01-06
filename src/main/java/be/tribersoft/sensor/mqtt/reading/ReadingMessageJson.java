package be.tribersoft.sensor.mqtt.reading;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;

public class ReadingMessageJson implements ReadingMessage {

	@JsonProperty
	private BigDecimal value;

	@Override
	public BigDecimal getValue() {
		return value;
	}

}
