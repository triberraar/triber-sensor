package be.tribersoft.sensor.domain.api.sensor;

import javax.validation.constraints.NotNull;

public interface SensorMessage extends SensorUpdateMessage {

	@NotNull(message = "sensor.validation.type.null")
	String getTypeId();

	@NotNull(message = "sensor.validation.unit.null")
	String getUnitId();

}
