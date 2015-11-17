package be.tribersoft.sensor.domain.impl.type;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeJpaRepository extends JpaRepository<TypeEntity, String> {
	List<TypeEntity> findAllByOrderByCreationDateDesc();

	Optional<TypeEntity> findById(String id);
}
