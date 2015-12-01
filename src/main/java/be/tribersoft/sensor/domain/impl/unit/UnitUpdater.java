package be.tribersoft.sensor.domain.impl.unit;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@Named
public class UnitUpdater {

	public void update(UnitEntity unit, UnitMessage unitMessage) {
		unit.setName(unitMessage.getName());
		unit.setSymbol(unitMessage.getSymbol());
	}

}
