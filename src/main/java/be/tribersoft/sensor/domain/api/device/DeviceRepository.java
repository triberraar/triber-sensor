package be.tribersoft.sensor.domain.api.device;

import java.util.List;

public interface DeviceRepository {

	Device getById(String id);

	List<? extends Device> all();

	boolean exists(String id);

}
