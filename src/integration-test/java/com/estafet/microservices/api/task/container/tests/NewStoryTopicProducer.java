package com.estafet.microservices.api.task.container.tests;

public class NewStoryTopicProducer extends TopicConsumer {

	public NewStoryTopicProducer() {
		super("new.task.topic");
	}

}
