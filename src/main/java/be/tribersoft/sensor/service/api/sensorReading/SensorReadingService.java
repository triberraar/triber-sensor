package be.tribersoft.sensor.service.api.sensorReading;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;

public interface SensorReadingService {

	void save(String sensorId, SensorReadingMessage sensorReadingMessage);

}
