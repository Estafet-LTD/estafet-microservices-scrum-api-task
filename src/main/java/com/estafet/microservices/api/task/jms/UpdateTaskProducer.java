package com.estafet.microservices.api.task.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.task.model.Task;

@Component
public class UpdateTaskProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Task task) {
		jmsTemplate.convertAndSend("update.task.topic", task.toJSON());
	}
	
}
