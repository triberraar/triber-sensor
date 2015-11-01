package be.tribersoft.sensor.domain.impl.sensor;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.sensor.exception.SensorNotFoundException;

@Named
public class SensorRepositoryImpl {
	@Inject
	private SensorJpaRepository sensorJpaRepository;

	public List<SensorEntity> all() {
		return sensorJpaRepository.findAllByOrderByCreationDateDesc();
	}

	public void save(SensorEntity sensor) {
		sensorJpaRepository.save(sensor);
	}

	public void delete(SensorEntity sensor) {
		sensorJpaRepository.delete(sensor);
	}

	public SensorEntity getByIdAndVersion(String id, Long version) {
		SensorEntity sensor = getById(id);
		if (!sensor.getVersion().equals(version)) {
			throw new ConcurrentModificationException();
		}
		return sensor;
	}

	public SensorEntity getById(String id) {
		Optional<SensorEntity> sensor = sensorJpaRepository.findById(id);
		if (!sensor.isPresent()) {
			throw new SensorNotFoundException();
		}
		return sensor.get();
	}
}
