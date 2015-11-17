package be.tribersoft.sensor.domain.impl.sensor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorJpaRepository extends JpaRepository<SensorEntity, String> {
	List<SensorEntity> findAllByOrderByCreationDateDesc();

	Long countByUnitId(String unitId);

	Long countByTypeId(String typeId);

	List<SensorEntity> findAllByDeviceIdOrderByCreationDateDesc(String deviceId);

	Optional<SensorEntity> findByDeviceIdAndId(String deviceId, String id);

	Optional<SensorEntity> findById(String id);
}