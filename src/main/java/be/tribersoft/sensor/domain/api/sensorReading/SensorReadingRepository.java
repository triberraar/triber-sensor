package be.tribersoft.sensor.domain.api.sensorReading;

import java.util.List;

public interface SensorReadingRepository {

	List<? extends SensorReading> allBySensor(String sensorId);

}
