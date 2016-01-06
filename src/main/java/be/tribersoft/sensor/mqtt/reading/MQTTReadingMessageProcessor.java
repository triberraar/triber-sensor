package be.tribersoft.sensor.mqtt.reading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.mqtt.exception.UnprocessableMqttMessageException;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@Named
public class MQTTReadingMessageProcessor implements MessageHandler {

	private static final String TOPIC_REGEX = "device\\/([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})\\/sensor\\/([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})\\/reading";
	private static final String MQTT_TOPIC = "mqtt_topic";
	@Inject
	private ReadingService readingService;
	@Inject
	private SensorValidator sensorValidator;

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		Pattern pattern = Pattern.compile(TOPIC_REGEX);
		Matcher m = pattern.matcher((String) message.getHeaders().get(MQTT_TOPIC));
		if (m.matches()) {
			String deviceId = m.group(1);
			String sensorId = m.group(2);
			sensorValidator.validate(deviceId, sensorId);
			readingService.save(sensorId, (ReadingMessage) message.getPayload());
		} else {
			throw new UnprocessableMqttMessageException();
		}
	}

}
