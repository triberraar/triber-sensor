package be.tribersoft.sensor.domain.api.event;

public enum EventSubject {
	DEVICE("event.device") {
		@Override
		public void accept(EventSubjectVisitor visitor, Event event) {
			visitor.visitDevice(event);
		}
	},
	SENSOR("event.sensor") {
		@Override
		public void accept(EventSubjectVisitor visitor, Event event) {
			visitor.visitSensor(event);
		}
	},
	READING("event.reading") {
		@Override
		public void accept(EventSubjectVisitor visitor, Event event) {
			visitor.visitReading(event);
		}
	};

	private String message;

	private EventSubject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public abstract void accept(EventSubjectVisitor visitor, Event event);
}
