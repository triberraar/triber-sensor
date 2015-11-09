package be.tribersoft.sensor.service.api.sensor;

import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

public interface SensorService {

	void save(String deviceId, SensorMessage sensorMessage);

	void update(String id, Long version, SensorUpdateMessage sensorUpdateMessage);

	void delete(String id, Long version);

}
