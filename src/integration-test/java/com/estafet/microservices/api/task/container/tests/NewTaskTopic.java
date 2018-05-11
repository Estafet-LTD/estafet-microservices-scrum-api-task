package com.estafet.microservices.api.task.container.tests;

public class NewTaskTopic extends JMSTopic {

	public NewTaskTopic() {
		super("new.task.topic");
	}
	
	public static String consume() {
		return new NewTaskTopic().consumeMessage();
	}

}
