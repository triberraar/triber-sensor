package be.tribersoft.sensor.domain.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.device.DeviceRepositoryImpl;
import be.tribersoft.sensor.domain.impl.type.TypeRepositoryImpl;
import be.tribersoft.sensor.domain.impl.unit.UnitRepositoryImpl;

@Named
public class SensorFactory {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private UnitRepositoryImpl unitRepository;
	@Inject
	private DeviceRepositoryImpl deviceRepository;

	public SensorEntity create(String deviceId, SensorMessage sensorMessage) {
		SensorEntity sensorEntity = new SensorEntity(sensorMessage.getName(), deviceRepository.getById(deviceId), typeRepository.getById(sensorMessage.getTypeId()), unitRepository.getById(sensorMessage.getUnitId()));
		sensorEntity.setDescription(sensorMessage.getDescription());
		return sensorEntity;
	}

}
