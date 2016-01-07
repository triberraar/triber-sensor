package be.tribersoft.spring.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

@RunWith(MockitoJUnitRunner.class)
public class MqttHealthIndicatorHealthTest {

	private static final String MESSAGE = "message";
	@InjectMocks
	private MqttHealthIndicator mqttHealthIndicator;
	@Mock
	private MqttClientFactory mqttClientFactory;
	@Mock
	private MqttClient mqttClient;
	@Mock
	private MqttException mqttException;

	@Before
	public void setUp() throws MqttException {
		when(mqttClientFactory.create()).thenReturn(mqttClient);
	}

	@Test
	public void isHealthyWhenNoExceptionOccurs() {
		Health result = mqttHealthIndicator.health();

		assertThat(result.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	public void isNotHealthyWhenExceptionOccurs() throws MqttSecurityException, MqttException {
		doThrow(mqttException).when(mqttClient).connect(Mockito.any(MqttConnectOptions.class));
		when(mqttException.getMessage()).thenReturn(MESSAGE);

		Health result = mqttHealthIndicator.health();

		assertThat(result.getStatus()).isEqualTo(Status.DOWN);
		assertThat(result.getDetails().get("error code")).isEqualTo(MESSAGE);

	}

}
