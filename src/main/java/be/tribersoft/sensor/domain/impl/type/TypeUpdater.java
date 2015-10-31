package be.tribersoft.sensor.domain.impl.type;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

@Named
public class TypeUpdater {

	public void update(TypeEntity type, TypeMessage typeMessage) {
		type.setName(typeMessage.getName());
	}

}
