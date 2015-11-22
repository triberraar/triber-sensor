package be.tribersoft.sensor.domain.api.event;

public enum EventSubject {
	DEVICE("event.device"), SENSOR("event.sensor"), READING("event.reading");

	private String message;

	private EventSubject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
