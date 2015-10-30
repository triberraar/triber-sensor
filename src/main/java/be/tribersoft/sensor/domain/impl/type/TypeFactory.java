package be.tribersoft.sensor.domain.impl.type;

import javax.inject.Named;
import javax.validation.Valid;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

@Named
public class TypeFactory {

	@Valid
	public TypeEntity create(@Valid TypeMessage typeCreate) {
		return new TypeEntity(typeCreate.getName());
	}
}
