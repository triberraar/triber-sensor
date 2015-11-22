package be.tribersoft.sensor.domain.api.event;

public enum EventMode {
	CREATED("event.created"), UPDATED("event.updated"), DELETED("event.deleted");

	private String message;

	private EventMode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
