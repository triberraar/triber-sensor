package be.tribersoft.sensor.domain.api.reading;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ReadingRepository {

	List<? extends Reading> allBySensor(String sensorId, Pageable pageable);

	int countBySensor(String sensorId);

}
