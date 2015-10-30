package be.tribersoft.sensor.rest.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

public class TypePostJson implements TypeMessage {

	private String name;

	@Override
	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

}
