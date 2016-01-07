package be.tribersoft.spring.health;

import javax.inject.Named;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;

@Named
public class MqttClientFactory {

	@Value("${mqtt.broker.ip}")
	private String brokerIp;
	@Value("${mqtt.broker.port}")
	private int brokerPort;

	public MqttClient create() throws MqttException {
		return new MqttClient("tcp://" + brokerIp + ":" + brokerPort, "triber-sensor-health-indicator");
	}

}
