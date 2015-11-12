package be.tribersoft.sensor.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiToJsonAdapter {
	public static final String API = "api";
	private String version;

	public ApiToJsonAdapter(String version) {
		this.version = version;
	}

	@JsonProperty
	public String getVersion() {
		return version;
	}
}
