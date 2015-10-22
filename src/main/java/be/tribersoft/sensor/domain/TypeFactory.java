package be.tribersoft.sensor.domain;

import javax.inject.Named;
import javax.validation.Valid;

import be.tribersoft.sensor.domain.api.Type;

@Named
public class TypeFactory {

	@Valid
	public TypeEntity create(Type type) {
		return new TypeEntity(type.getName());
	}
}
