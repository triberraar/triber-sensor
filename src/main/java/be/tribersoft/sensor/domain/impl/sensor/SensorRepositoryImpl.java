package be.tribersoft.sensor.domain.impl.sensor;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.sensor.exception.SensorNotFoundException;

@Named
public class SensorRepositoryImpl implements SensorRepository {
	@Inject
	private SensorJpaRepository sensorJpaRepository;

	@Override
	public List<SensorEntity> all() {
		return sensorJpaRepository.findAllByOrderByCreationDateDesc();
	}

	public void save(SensorEntity sensor) {
		sensorJpaRepository.save(sensor);
	}

	public void delete(SensorEntity sensor) {
		sensorJpaRepository.delete(sensor);
	}

	@Override
	public boolean unitInUse(String unitId) {
		return sensorJpaRepository.countByUnitId(unitId) != 0;
	}

	@Override
	public boolean typeInUse(String typeId) {
		return sensorJpaRepository.countByTypeId(typeId) != 0;
	}

	@Override
	public List<? extends Sensor> allByDevice(String deviceId) {
		return sensorJpaRepository.findAllByDeviceIdOrderByCreationDateDesc(deviceId);
	}

	public SensorEntity getByDeviceIdAndIdAndVersion(String deviceId, String id, Long version) {
		SensorEntity sensorEntity = getByDeviceIdAndId(deviceId, id);
		if (sensorEntity.getVersion() != version) {
			throw new ConcurrentModificationException();
		}
		return sensorEntity;
	}

	@Override
	public SensorEntity getByDeviceIdAndId(String deviceId, String id) {
		Optional<SensorEntity> sensorEntity = sensorJpaRepository.findByDeviceIdAndId(deviceId, id);
		if (!sensorEntity.isPresent()) {
			throw new SensorNotFoundException();
		}
		return sensorEntity.get();
	}
}
