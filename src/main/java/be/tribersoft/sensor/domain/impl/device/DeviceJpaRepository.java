package be.tribersoft.sensor.domain.impl.device;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface DeviceJpaRepository extends Repository<DeviceEntity, String> {
	List<DeviceEntity> findAllByOrderByCreationDateDesc();

	void save(DeviceEntity sensor);

	Optional<DeviceEntity> findById(String id);

	void delete(DeviceEntity sensor);
}