package be.tribersoft.sensor.domain.impl.device;

import javax.inject.Named;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@Named
public class DeviceFactory {

	public DeviceEntity create(DeviceMessage deviceMessage) {
		DeviceEntity deviceEntity = new DeviceEntity(deviceMessage.getName());
		deviceEntity.setDescription(deviceMessage.getDescription());
		deviceEntity.setLocation(deviceMessage.getLocation());
		return deviceEntity;
	}

}
