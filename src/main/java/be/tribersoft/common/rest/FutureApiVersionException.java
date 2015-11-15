package be.tribersoft.common.rest;

public class FutureApiVersionException extends RuntimeException {

	private String currentApiVersion;
	private String apiVersion;

	public FutureApiVersionException(String currentApiVersion, String apiVersion) {
		this.currentApiVersion = currentApiVersion;
		this.apiVersion = apiVersion;
	}

	public String getCurrentApiVersion() {
		return currentApiVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}

}
