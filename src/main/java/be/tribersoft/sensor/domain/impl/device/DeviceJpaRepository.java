package be.tribersoft.sensor.domain.impl.device;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, String> {
	List<DeviceEntity> findAllByOrderByCreationDateDesc();

	Optional<DeviceEntity> findById(String id);

}