package be.tribersoft.sensor.rest;

import be.tribersoft.sensor.domain.api.TypeCreate;

public class TypePostJson implements TypeCreate {

	private String name;

	@Override
	public String getName() {
		return name;
	}

}
