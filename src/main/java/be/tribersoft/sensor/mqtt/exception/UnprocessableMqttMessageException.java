package be.tribersoft.sensor.mqtt.exception;

public class UnprocessableMqttMessageException extends RuntimeException {

	public UnprocessableMqttMessageException() {
		super("Can not process mqtt message");
	}

}
