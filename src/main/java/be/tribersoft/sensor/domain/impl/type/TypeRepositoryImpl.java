package be.tribersoft.sensor.domain.impl.type;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.type.TypeRepository;
import be.tribersoft.sensor.domain.api.type.exception.TypeNotFoundException;

@Named
public class TypeRepositoryImpl implements TypeRepository {

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Override
	public List<TypeEntity> all() {
		return typeJpaRepository.findAllByOrderByCreationDateDesc();
	}

	public void save(TypeEntity type) {
		typeJpaRepository.save(type);
	}

	public void delete(TypeEntity type) {
		typeJpaRepository.delete(type);
	}

	public TypeEntity getByIdAndVersion(String id, Long version) {
		TypeEntity type = getById(id);
		if (!type.getVersion().equals(version)) {
			throw new ConcurrentModificationException();
		}
		return type;
	}

	@Override
	public TypeEntity getById(String id) {
		Optional<TypeEntity> type = typeJpaRepository.findById(id);
		if (!type.isPresent()) {
			throw new TypeNotFoundException();
		}
		return type.get();
	}
}
