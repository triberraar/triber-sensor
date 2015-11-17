package be.tribersoft.sensor.domain.impl.unit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitJpaRepository extends JpaRepository<UnitEntity, String> {
	List<UnitEntity> findAllByOrderByCreationDateDesc();

	Optional<UnitEntity> findById(String id);

}
