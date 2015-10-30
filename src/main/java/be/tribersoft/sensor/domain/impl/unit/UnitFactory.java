package be.tribersoft.sensor.domain.impl.unit;

import javax.inject.Named;
import javax.validation.Valid;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@Named
public class UnitFactory {

	@Valid
	public UnitEntity create(@Valid UnitMessage unitMessage) {
		UnitEntity unitEntity = new UnitEntity(unitMessage.getName());
		unitEntity.setSymbol(unitMessage.getSymbol());
		return unitEntity;
	}
}
