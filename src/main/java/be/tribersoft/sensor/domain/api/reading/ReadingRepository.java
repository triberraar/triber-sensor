package be.tribersoft.sensor.domain.api.reading;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadingRepository {

	Page<? extends Reading> allBySensor(String sensorId, Pageable pageable);

	Reading getById(String id);

}
