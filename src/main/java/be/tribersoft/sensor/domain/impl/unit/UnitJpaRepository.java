package be.tribersoft.sensor.domain.impl.unit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface UnitJpaRepository extends Repository<UnitEntity, String> {
	List<UnitEntity> findAllByOrderByCreationDateDesc();

	void save(UnitEntity unit);

	Optional<UnitEntity> findById(String id);

	void delete(UnitEntity unit);
}
