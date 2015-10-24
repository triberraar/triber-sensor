package be.tribersoft.sensor.domain;

import javax.inject.Named;
import javax.validation.Valid;

import be.tribersoft.sensor.domain.api.TypeCreate;

@Named
public class TypeFactory {

	@Valid
	public TypeEntity create(TypeCreate typeCreate) {
		return new TypeEntity(typeCreate.getName());
	}
}
