package be.tribersoft.sensor.rest.type;

import be.tribersoft.sensor.domain.api.type.TypeCreate;

public class TypePostJson implements TypeCreate {

	private String name;

	@Override
	public String getName() {
		return name;
	}

}
