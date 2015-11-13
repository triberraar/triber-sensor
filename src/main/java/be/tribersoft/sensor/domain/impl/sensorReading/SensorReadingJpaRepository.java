package be.tribersoft.sensor.domain.impl.sensorReading;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface SensorReadingJpaRepository extends Repository<SensorReadingEntity, String> {
	void save(SensorReadingEntity sensor);

	void delete(SensorReadingEntity sensor);

	List<SensorReadingEntity> findAllBySensorIdOrderByCreationDateDesc(String sensorId);

	Optional<SensorReadingEntity> findBySensorIdAndId(String sensorId, String id);

}