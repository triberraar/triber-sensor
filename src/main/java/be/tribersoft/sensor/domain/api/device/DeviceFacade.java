package be.tribersoft.sensor.domain.api.device;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

public interface DeviceFacade {
	void save(DeviceMessage deviceMessage);

	void update(String id, Long version, DeviceMessage deviceMessage);

	void delete(String id, Long version);
}
