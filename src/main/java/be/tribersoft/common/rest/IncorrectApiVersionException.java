package be.tribersoft.common.rest;

public class IncorrectApiVersionException extends RuntimeException {

	private String currentApiVersion;
	private String apiVersion;

	public IncorrectApiVersionException(String currentApiVersion, String apiVersion) {
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
