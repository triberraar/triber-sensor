package be.tribersoft.sensor.domain;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.TypeRepository;
import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.exception.TypeNotFoundException;

@Named
public class TypeRepositoryImpl implements TypeRepository {

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Override
	public List<TypeEntity> all() {
		return typeJpaRepository.findAll();
	}

	public void save(TypeEntity type) {
		typeJpaRepository.save(type);
	}

	public void delete(TypeEntity type) {
		typeJpaRepository.delete(type);
	}

	public TypeEntity getByUuidAndVersion(String uuid, Long version) {
		TypeEntity type = getByUuid(uuid);
		if (!type.getVersion().equals(version)) {
			throw new ConcurrentModificationException();
		}
		return type;
	}

	@Override
	public TypeEntity getByUuid(String uuid) {
		Optional<TypeEntity> type = typeJpaRepository.findByUuid(uuid);
		if (!type.isPresent()) {
			throw new TypeNotFoundException();
		}
		return type.get();
	}
}
