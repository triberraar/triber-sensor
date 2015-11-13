package be.tribersoft.sensor.service.impl.sensorReading;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingFacade;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;
import be.tribersoft.sensor.service.api.sensorReading.SensorReadingService;

@Named
@Transactional
public class SensorReadingServiceImpl implements SensorReadingService {

	@Inject
	private SensorReadingFacade sensorReadingFacade;

	@Override
	public void save(String sensorId, SensorReadingMessage sensorReadingMessage) {
		sensorReadingFacade.save(sensorId, sensorReadingMessage);
	}

}
