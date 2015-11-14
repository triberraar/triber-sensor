package be.tribersoft.sensor.domain.api.reading;

public interface ReadingFacade {

	void save(String sensorId, ReadingMessage readingMessage);

	void deleteBySensor(String sensorId);

}
