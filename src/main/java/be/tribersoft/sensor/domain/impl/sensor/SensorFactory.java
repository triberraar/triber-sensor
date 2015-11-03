package be.tribersoft.sensor.domain.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.type.TypeRepositoryImpl;
import be.tribersoft.sensor.domain.impl.unit.UnitRepositoryImpl;

@Named
public class SensorFactory {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private UnitRepositoryImpl unitRepository;

	public SensorEntity create(SensorMessage sensorMessage) {
		SensorEntity sensorEntity = new SensorEntity(sensorMessage.getName(), typeRepository.getById(sensorMessage.getTypeId()), unitRepository.getById(sensorMessage.getUnitId()));
		sensorEntity.setDescription(sensorMessage.getDescription());
		return sensorEntity;
	}

}
