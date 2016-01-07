package be.tribersoft.spring.health;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

@RunWith(MockitoJUnitRunner.class)
public class MqttClientFactoryCreateTest {

	private static final int PORT = 1883;
	private static final String IP = "localhost";
	@InjectMocks
	private MqttClientFactory mqttClientFactory;
	@Value("${mqtt.broker.ip}")
	private String brokerIp;
	@Value("${mqtt.broker.port}")
	private int brokerPort;

	@Before
	public void setUp() {
		Whitebox.setInternalState(mqttClientFactory, "brokerIp", IP);
		Whitebox.setInternalState(mqttClientFactory, "brokerPort", PORT);
	}

	@Test
	public void createsAnMqttClientWithConfiguredOptions() throws MqttException {
		MqttClient result = mqttClientFactory.create();

		assertThat(result.getServerURI()).isEqualTo("tcp://" + IP + ":" + PORT);
	}
}
