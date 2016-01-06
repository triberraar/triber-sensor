package be.tribersoft.sensor.rest.sensor;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

public class SensorUpdateJson implements SensorUpdateMessage {

	@JsonProperty
	private Long version;
	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> description;

	public Long getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<String> getDescription() {
		if (description == null) {
			description = Optional.empty();
		}
		return description;
	}

}
