package be.tribersoft.sensor.rest.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.TypeCreate;

public class TypePostJson implements TypeCreate {

	private String name;

	@Override
	@JsonProperty
	public String getName() {
		return name;
	}

}
