package be.tribersoft.sensor.domain.impl.device;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@Named
public class DeviceUpdater {

	public void update(DeviceEntity device, DeviceMessage deviceMessage) {
		device.setName(deviceMessage.getName());
		device.setDescription(deviceMessage.getDescription());
		device.setLocation(deviceMessage.getLocation());
	}

}
