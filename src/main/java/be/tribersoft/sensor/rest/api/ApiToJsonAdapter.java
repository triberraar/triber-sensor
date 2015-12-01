package be.tribersoft.sensor.rest.api;

public class ApiToJsonAdapter {
	public static final String API = "api";
	private String version;

	public ApiToJsonAdapter(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
}
