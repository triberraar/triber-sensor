package be.tribersoft.sensor.rest.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

public class TypePostJson implements TypeMessage {

	@JsonProperty
	private String name;

	@Override
	public String getName() {
		return name;
	}

}
