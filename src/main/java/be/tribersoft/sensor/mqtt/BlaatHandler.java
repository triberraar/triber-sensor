package be.tribersoft.sensor.mqtt;

import javax.inject.Named;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Named
public class BlaatHandler implements MessageHandler {

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		System.out.println(message.getPayload());
		System.out.println(message.getHeaders());
	}

}
