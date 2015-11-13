package be.tribersoft.sensor.domain.api.sensorReading;

public interface SensorReadingFacade {

	void save(String sensorId, SensorReadingMessage sensorReadingMessage);

}
