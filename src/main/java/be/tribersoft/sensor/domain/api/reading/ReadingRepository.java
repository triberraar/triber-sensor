package be.tribersoft.sensor.domain.api.reading;

import java.util.List;

public interface ReadingRepository {

	List<? extends Reading> allBySensor(String sensorId);

}
