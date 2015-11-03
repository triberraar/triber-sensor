package be.tribersoft.sensor.domain.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.type.TypeRepositoryImpl;
import be.tribersoft.sensor.domain.impl.unit.UnitRepositoryImpl;

@Named
public class SensorUpdater {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private UnitRepositoryImpl unitRepository;

	public void update(SensorEntity sensor, SensorMessage sensorMessage) {
		sensor.setName(sensorMessage.getName());
		sensor.setDescription(sensorMessage.getDescription());
		sensor.setType(typeRepository.getById(sensorMessage.getTypeId()));
		sensor.setUnit(unitRepository.getById(sensorMessage.getUnitId()));
	}
}
