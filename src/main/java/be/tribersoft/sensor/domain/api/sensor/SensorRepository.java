package be.tribersoft.sensor.domain.api.sensor;

import java.util.List;

public interface SensorRepository {

	Sensor getById(String id);

	List<? extends Sensor> all();

	boolean unitInUse(String unitId);

	boolean typeInUse(String typeId);

	List<? extends Sensor> allByDevice(String deviceId);
}
