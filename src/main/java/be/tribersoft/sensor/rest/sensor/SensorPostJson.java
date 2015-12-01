package be.tribersoft.sensor.rest.sensor;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;

public class SensorPostJson implements SensorMessage {

	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> description;
	@JsonProperty
	private String typeId;
	@JsonProperty
	private String unitId;

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

	@Override
	public String getTypeId() {
		return typeId;
	}

	@Override
	public String getUnitId() {
		return unitId;
	}

}
