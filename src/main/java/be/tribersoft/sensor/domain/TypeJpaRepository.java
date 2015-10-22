package be.tribersoft.sensor.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface TypeJpaRepository extends Repository<TypeEntity, String> {
	List<TypeEntity> findAll();

	void save(TypeEntity type);

	Optional<TypeEntity> findByUuid(String uuid);

	void delete(TypeEntity type);
}
