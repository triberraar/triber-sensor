package be.tribersoft.sensor.domain.impl.sensor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface SensorJpaRepository extends Repository<SensorEntity, String> {
	List<SensorEntity> findAllByOrderByCreationDateDesc();

	void save(SensorEntity sensor);

	void delete(SensorEntity sensor);

	Long countByUnitId(String unitId);

	Long countByTypeId(String typeId);

	List<SensorEntity> findAllByDeviceIdOrderByCreationDateDesc(String deviceId);

	Optional<SensorEntity> findByDeviceIdAndId(String deviceId, String id);

	Optional<SensorEntity> findById(String id);
}