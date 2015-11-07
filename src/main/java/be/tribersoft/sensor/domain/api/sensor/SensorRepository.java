package be.tribersoft.sensor.domain.api.sensor;

import java.util.List;

import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

public interface SensorRepository {

	Sensor getById(String id);

	List<? extends SensorEntity> all();

	boolean unitInUse(String unitId);

	boolean typeInUse(String typeId);
}
