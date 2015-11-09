package be.tribersoft.sensor.domain.api.sensor;

public interface SensorFacade {

	void save(String deviceId, SensorMessage sensorMessage);

	void update(String id, Long version, SensorUpdateMessage sensorUpdateMessage);

	void delete(String id, Long version);

}
