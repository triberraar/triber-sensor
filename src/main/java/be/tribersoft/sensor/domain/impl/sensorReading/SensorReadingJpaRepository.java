package be.tribersoft.sensor.domain.impl.sensorReading;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface SensorReadingJpaRepository extends Repository<SensorReadingEntity, String> {
	void save(SensorReadingEntity sensor);

	void delete(SensorReadingEntity sensor);

	List<SensorReadingEntity> findAllBySensorIdOrderByCreationDateDesc(String sensorId);

}