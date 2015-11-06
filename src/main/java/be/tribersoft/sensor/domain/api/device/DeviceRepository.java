package be.tribersoft.sensor.domain.api.device;

import java.util.List;

import be.tribersoft.sensor.domain.impl.device.DeviceEntity;

public interface DeviceRepository {

	Device getById(String id);

	List<? extends DeviceEntity> all();

}
