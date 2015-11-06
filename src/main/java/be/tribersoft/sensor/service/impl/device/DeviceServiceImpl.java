package be.tribersoft.sensor.service.impl.device;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.domain.api.type.DeviceMessage;
import be.tribersoft.sensor.service.api.device.DeviceService;

@Named
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Inject
	private DeviceFacade deviceFacade;

	@Override
	public void save(DeviceMessage deviceMessage) {
		deviceFacade.save(deviceMessage);
	}

	@Override
	public void update(String id, Long version, DeviceMessage deviceMessage) {
		deviceFacade.update(id, version, deviceMessage);
	}

	@Override
	public void delete(String id, Long version) {
		deviceFacade.delete(id, version);
	}

}
