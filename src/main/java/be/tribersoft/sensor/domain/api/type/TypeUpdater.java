package be.tribersoft.sensor.domain.api.type;

import javax.inject.Named;

import be.tribersoft.sensor.domain.impl.type.TypeEntity;

@Named
public class TypeUpdater {

	public void update(TypeEntity type, TypeUpdate typeUpdate) {
		type.setName(typeUpdate.getName());
	}

}
