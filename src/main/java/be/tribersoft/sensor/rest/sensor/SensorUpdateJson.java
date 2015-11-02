package be.tribersoft.sensor.rest.sensor;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;

public class SensorUpdateJson implements SensorMessage {

	@JsonProperty
	@NotNull(message = "sensor.validation.version.null")
	private Long version;
	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> description;
	@JsonProperty
	private String typeId;
	@JsonProperty
	private String unitId;

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

	@Override
	public String getTypeId() {
		return typeId;
	}

	@Override
	public String getUnitId() {
		return unitId;
	}

}
