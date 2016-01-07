package be.tribersoft.sensor.mqtt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

public class MqttIntegrationFlowMqttSensorProducerTest {

	private MqttIntegrationFlow flow = new MqttIntegrationFlow();

	@Test
	public void createsAPRoducerThatTakesMQTTMessageAndPassesThemToTheInputChannel() {
		MessageProducer mqttSensorProducer = flow.mqttSensorProducer();

		assertThat(mqttSensorProducer).isInstanceOf(MqttPahoMessageDrivenChannelAdapter.class);
		MqttPahoMessageDrivenChannelAdapter adapter = (MqttPahoMessageDrivenChannelAdapter) mqttSensorProducer;
		assertThat(adapter.getTopic()).hasSize(1).contains("device/+/sensor/+/reading");

	}
}
