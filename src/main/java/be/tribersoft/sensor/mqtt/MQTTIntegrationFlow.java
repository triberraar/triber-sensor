package be.tribersoft.sensor.mqtt;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import be.tribersoft.sensor.mqtt.reading.MQTTReadingMessageProcessor;
import be.tribersoft.sensor.mqtt.reading.ReadingMessageJson;

@Configuration
@Profile("MQTT")
public class MQTTIntegrationFlow {
	@Inject
	private MQTTReadingMessageProcessor mqttSensorMessageProcessor;
	@Value("${mqtt.broker.ip}")
	private String brokerIp;
	@Value("${mqtt.broker.port}")
	private int brokerPort;

	@Bean
	public MessageChannel inputMqttSensorChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer mqttSensorProducer() {
		String clientName = "triber-sensor-" + UUID.randomUUID().toString();
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("tcp://" + brokerIp + ":" + brokerPort, clientName, "device/+/sensor/+/reading");
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(inputMqttSensorChannel());

		return adapter;
	}

	@Bean
	public IntegrationFlow mqttSensorInputFlow() {
		// @formatter:off
		return IntegrationFlows
				.from(inputMqttSensorChannel())
				.transform(Transformers.fromJson(ReadingMessageJson.class))
				.handle(mqttSensorMessageProcessor).get();
		// @formatter:on
	}

}
