package be.tribersoft.sensor.rest.unit;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitDeleteJson {

	@JsonProperty
	@NotNull(message = "unit.validation.version.null")
	private Long version;

	public Long getVersion() {
		return version;
	}

}
