package be.tribersoft.spring.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;

@Named
@Profile("MQTT")
public class MqttHealthIndicator implements HealthIndicator {

	@Inject
	private MqttClientFactory mqttClientFactory;

	@Override
	public Health health() {
		try {
			MqttClient client = mqttClientFactory.create();
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			client.connect(connOpts);
			client.disconnect();
			return Health.up().build();
		} catch (MqttException e) {
			return Health.down().withDetail("error code", e.getMessage()).build();
		}
	}

}
