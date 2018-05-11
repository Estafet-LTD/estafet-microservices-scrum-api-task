package com.estafet.microservices.api.task.container.tests;

public class UpdatedStoryTopicProducer extends TopicProducer {

	public UpdatedStoryTopicProducer() {
		super("update.task.topic");
	}
	
	public static void send(String message) {
		new UpdatedStoryTopicProducer().sendMessage(message);
	}

}
