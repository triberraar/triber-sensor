package be.tribersoft.sensor.domain.impl.sensorReading;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingFacade;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;

@Named
public class SensorReadingFacadeImpl implements SensorReadingFacade {

	@Inject
	private SensorReadingFactory sensorReadingFactory;
	@Inject
	private SensorReadingRepositoryImpl sensorReadingRepository;

	@Override
	public void save(String sensorId, SensorReadingMessage sensorReadingMessage) {
		sensorReadingRepository.save(sensorReadingFactory.create(sensorId, sensorReadingMessage));
	}

}
