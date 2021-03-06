package be.tribersoft.sensor.rest.device;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

public class DevicePostJson implements DeviceMessage {

	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> description;
	@JsonProperty
	private Optional<String> location;

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
	public Optional<String> getLocation() {
		if (location == null) {
			location = Optional.empty();
		}
		return location;
	}

}
