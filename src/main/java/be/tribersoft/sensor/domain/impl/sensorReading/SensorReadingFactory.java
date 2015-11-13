package be.tribersoft.sensor.domain.impl.sensorReading;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;
import be.tribersoft.sensor.domain.impl.sensor.SensorRepositoryImpl;

@Named
public class SensorReadingFactory {

	@Inject
	private SensorRepositoryImpl sensorRepository;

	public SensorReadingEntity create(String sensorId, SensorReadingMessage sensorReadingMessage) {
		SensorReadingEntity sensorReadingEntity = new SensorReadingEntity(sensorReadingMessage.getValue(), sensorRepository.getById(sensorId));
		return sensorReadingEntity;
	}

}
