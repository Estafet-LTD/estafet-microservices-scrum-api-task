package com.estafet.microservices.api.task.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import com.estafet.microservices.api.task.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UpdateTaskProducer extends JmsGatewaySupport {

	public void sendMessage(final Task task) {
		getJmsTemplate().send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				try {
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String json = ow.writeValueAsString(task);
					TextMessage message = session.createTextMessage(json);
					return message;
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
