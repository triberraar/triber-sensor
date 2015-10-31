package be.tribersoft.sensor.domain.impl.unit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.unit.UnitRepository;
import be.tribersoft.sensor.domain.api.unit.exception.UnitNotFoundException;

@Named
public class UnitRepositoryImpl implements UnitRepository {

	@Inject
	private UnitJpaRepository unitJpaRepository;

	@Override
	public List<UnitEntity> all() {
		return unitJpaRepository.findAllByOrderByCreationDateDesc();
	}

	public void save(UnitEntity unit) {
		unitJpaRepository.save(unit);
	}

	public void delete(UnitEntity unit) {
		unitJpaRepository.delete(unit);
	}

	public UnitEntity getByIdAndVersion(String id, Long version) {
		UnitEntity unit = getById(id);
		if (!unit.getVersion().equals(version)) {
			throw new ConcurrentModificationException();
		}
		return unit;
	}

	@Override
	public UnitEntity getById(String id) {
		Optional<UnitEntity> unit = unitJpaRepository.findById(id);
		if (!unit.isPresent()) {
			throw new UnitNotFoundException();
		}
		return unit.get();
	}
}
