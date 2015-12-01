package be.tribersoft.sensor.rest.device;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDeleteJson {

	@JsonProperty
	@NotNull(message = "device.validation.version.null")
	private Long version;

	public Long getVersion() {
		return version;
	}

}
