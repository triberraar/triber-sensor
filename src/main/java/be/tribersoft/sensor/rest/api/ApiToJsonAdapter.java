package be.tribersoft.sensor.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiToJsonAdapter {
	private String version;

	public ApiToJsonAdapter(String version) {
		this.version = version;
	}

	@JsonProperty
	public String getVersion() {
		return version;
	}
}
