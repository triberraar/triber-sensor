package be.tribersoft.sensor.service.api.reading;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;

public interface ReadingService {

	void save(String sensorId, ReadingMessage readingMessage);

}
