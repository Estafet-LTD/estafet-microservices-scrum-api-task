package com.estafet.microservices.api.task.jms;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.task.model.Task;

@Component
public class NewTaskProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Task task) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend("new.task.topic", task.toJSON(), new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("message.event.interaction.reference", UUID.randomUUID().toString());
				return message;
			}
		});
	}
}
