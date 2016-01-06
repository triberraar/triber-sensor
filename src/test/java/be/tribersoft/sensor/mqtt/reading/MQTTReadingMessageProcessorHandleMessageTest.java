package be.tribersoft.sensor.mqtt.reading;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.mqtt.exception.UnprocessableMqttMessageException;
import be.tribersoft.sensor.rest.sensor.SensorValidator;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@RunWith(MockitoJUnitRunner.class)
public class MQTTReadingMessageProcessorHandleMessageTest {
	private static final String INVALID_TOPIC = "invalid topic";
	private static final String MQTT_TOPIC = "mqtt_topic";

	@InjectMocks
	private MQTTReadingMessageProcessor mqttReadingMessageProcessor;

	@Mock
	private ReadingService readingService;
	@Mock
	private SensorValidator sensorValidator;

	@Mock
	private Message<ReadingMessage> message;
	@Mock
	private ReadingMessage readingMessage;

	@Test(expected = UnprocessableMqttMessageException.class)
	public void failsWhenTopicIsInvalid() {
		Map<String, Object> headers = new HashMap<>();
		headers.put(MQTT_TOPIC, INVALID_TOPIC);
		MessageHeaders messageHeaders = new MessageHeaders(headers);
		when(message.getHeaders()).thenReturn(messageHeaders);

		mqttReadingMessageProcessor.handleMessage(message);

	}

	@Test
	public void validatesAndSavesReading() {
		Map<String, Object> headers = new HashMap<>();
		String deviceId = UUID.randomUUID().toString();
		String sensorId = UUID.randomUUID().toString();
		headers.put(MQTT_TOPIC, "device/" + deviceId + "/sensor/" + sensorId + "/reading");
		MessageHeaders messageHeaders = new MessageHeaders(headers);
		when(message.getHeaders()).thenReturn(messageHeaders);
		when(message.getPayload()).thenReturn(readingMessage);

		mqttReadingMessageProcessor.handleMessage(message);

		verify(sensorValidator).validate(deviceId, sensorId);
		verify(readingService).save(sensorId, readingMessage);
	}
}
