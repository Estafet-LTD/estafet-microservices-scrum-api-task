package com.estafet.microservices.api.task.container.tests;

public class NewTaskTopicConsumer extends TopicConsumer {

	public NewTaskTopicConsumer() {
		super("new.task.topic");
	}

}
