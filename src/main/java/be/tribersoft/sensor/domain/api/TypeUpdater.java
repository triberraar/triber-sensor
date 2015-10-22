package be.tribersoft.sensor.domain.api;

import javax.inject.Named;

import be.tribersoft.sensor.domain.TypeEntity;

@Named
public class TypeUpdater {

	public void update(TypeEntity type, TypeUpdate typeUpdate) {
		type.setName(typeUpdate.getName());
	}

	public void patch(TypeEntity type, TypePatch typePatch) {
		if (typePatch.getName().isPresent()) {
			type.setName(typePatch.getName().get());
		}
	}

}
