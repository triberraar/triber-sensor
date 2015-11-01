package be.tribersoft.sensor.service.api.sensor;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;

public interface SensorService {

	void save(SensorMessage sensorMessage);

	void update(String id, Long version, SensorMessage sensorMessage);

	void delete(String id, Long version);

}
