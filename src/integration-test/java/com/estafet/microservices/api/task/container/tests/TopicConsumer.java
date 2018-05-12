package com.estafet.microservices.api.task.container.tests;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public abstract class TopicConsumer {

	Connection connection;
	MessageConsumer messageConsumer;
	String destination;
	
	public TopicConsumer(String destination) {
		try {
			this.destination = destination;
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(System.getenv("JBOSS_A_MQ_BROKER_URL"));
			connection = connectionFactory.createConnection(System.getenv("JBOSS_A_MQ_BROKER_USER"),
					System.getenv("JBOSS_A_MQ_BROKER_PASSWORD"));
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(destination);
			messageConsumer = session.createConsumer(topic);
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public String consumeMessage() throws JMSException {
		Message message = messageConsumer.receive(3000);
		return ((TextMessage) message).getText();
	}
}