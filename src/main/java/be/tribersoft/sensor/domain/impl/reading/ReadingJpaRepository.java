package be.tribersoft.sensor.domain.impl.reading;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingJpaRepository extends JpaRepository<ReadingEntity, String> {

	Page<ReadingEntity> findAllBySensorIdOrderByCreationDateDesc(String sensorId, Pageable pageable);

	List<ReadingEntity> findAllBySensorIdOrderByCreationDateDesc(String sensorId);

	Optional<ReadingEntity> findBySensorIdAndId(String sensorId, String id);

	Optional<ReadingEntity> findById(String id);

}