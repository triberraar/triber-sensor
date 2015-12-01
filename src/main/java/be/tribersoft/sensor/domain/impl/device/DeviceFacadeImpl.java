package be.tribersoft.sensor.domain.impl.device;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@Named
public class DeviceFacadeImpl implements DeviceFacade {

	@Inject
	private DeviceRepositoryImpl deviceRepository;
	@Inject
	private DeviceFactory deviceFactory;
	@Inject
	private DeviceUpdater deviceUpdater;

	@Override
	public void save(DeviceMessage deviceMessage) {
		deviceRepository.save(deviceFactory.create(deviceMessage));
	}

	@Override
	public void update(String id, Long version, DeviceMessage deviceMessage) {
		DeviceEntity device = deviceRepository.getByIdAndVersion(id, version);
		deviceUpdater.update(device, deviceMessage);
	}

	@Override
	public void delete(String id, Long version) {
		DeviceEntity device = deviceRepository.getByIdAndVersion(id, version);
		deviceRepository.delete(device);
	}

}
