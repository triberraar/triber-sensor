package be.tribersoft.sensor.mqtt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

public class MqttIntegrationFlowInputMqttSensorChannelTest {

	private MqttIntegrationFlow flow = new MqttIntegrationFlow();

	@Test
	public void returnsADireChannel() {
		MessageChannel inputMqttSensorChannel = flow.inputMqttSensorChannel();

		assertThat(inputMqttSensorChannel).isInstanceOf(DirectChannel.class);
	}

}
