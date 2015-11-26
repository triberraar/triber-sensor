package be.tribersoft.sensor.domain.impl.device;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.device.DeviceRepository;
import be.tribersoft.sensor.domain.api.device.exception.DeviceNotFoundException;
import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;

@Named
public class DeviceRepositoryImpl implements DeviceRepository {
	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Override
	public List<DeviceEntity> all() {
		return deviceJpaRepository.findAllByOrderByCreationDateDesc();
	}

	public void save(DeviceEntity sensor) {
		deviceJpaRepository.save(sensor);
	}

	public void delete(DeviceEntity sensor) {
		deviceJpaRepository.delete(sensor);
	}

	public DeviceEntity getByIdAndVersion(String id, Long version) {
		DeviceEntity device = getById(id);
		if (!device.getVersion().equals(version)) {
			throw new ConcurrentModificationException();
		}
		return device;
	}

	@Override
	public DeviceEntity getById(String id) {
		Optional<DeviceEntity> device = deviceJpaRepository.findById(id);
		if (!device.isPresent()) {
			throw new DeviceNotFoundException();
		}
		return device.get();
	}

	@Override
	public boolean exists(String id) {
		return deviceJpaRepository.exists(id);
	}
}
