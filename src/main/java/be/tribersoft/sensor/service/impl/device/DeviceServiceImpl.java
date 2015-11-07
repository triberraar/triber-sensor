package be.tribersoft.sensor.service.impl.device;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.device.DeviceFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.type.DeviceMessage;
import be.tribersoft.sensor.service.api.device.DeviceService;

@Named
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Inject
	private DeviceFacade deviceFacade;
	@Inject
	private SensorRepository sensorRepository;
	@Inject
	private SensorFacade sensorFacade;

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
		sensorRepository.allByDevice(id).stream().forEach(s -> sensorFacade.delete(id, s.getId(), s.getVersion()));
		deviceFacade.delete(id, version);
	}

}
