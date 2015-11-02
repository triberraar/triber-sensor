package be.tribersoft.sensor.rest.sensor;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorDeleteJson {

	@JsonProperty
	@NotNull(message = "sensor.validation.version.null")
	private Long version;

	public Long getVersion() {
		return version;
	}

}
