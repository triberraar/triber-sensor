package be.tribersoft.common.rest;

public class ErrorJson {

	private String message;
	private String key;

	public ErrorJson() {
	}

	public ErrorJson(String key, String message) {
		this.message = message;
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public String getKey() {
		return key;
	}

}
