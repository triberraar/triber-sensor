package be.tribersoft.sensor.domain.impl.reading;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface ReadingJpaRepository extends Repository<ReadingEntity, String> {
	void save(ReadingEntity sensor);

	void delete(ReadingEntity sensor);

	List<ReadingEntity> findAllBySensorIdOrderByCreationDateDesc(String sensorId);

	Optional<ReadingEntity> findBySensorIdAndId(String sensorId, String id);

}