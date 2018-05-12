package com.estafet.microservices.api.task.container.tests;

public class NewStoryTopicProducer extends TopicProducer {

	public NewStoryTopicProducer() {
		super("new.story.topic");
	}
	
	public static void send(String message) {
		new NewStoryTopicProducer().sendMessage(message);
	}

}
