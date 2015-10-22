package be.tribersoft.rest;

public class ErrorJson {

	private String message;

	public ErrorJson() {
	}

	public ErrorJson(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
