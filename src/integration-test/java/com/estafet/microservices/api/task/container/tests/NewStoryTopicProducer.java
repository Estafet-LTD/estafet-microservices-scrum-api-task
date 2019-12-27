package com.estafet.microservices.api.task.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicProducer;

public class NewStoryTopicProducer extends TopicProducer {

	public NewStoryTopicProducer() {
		super("new.story.topic");
	}
	
	public static void send(String message) {
		new NewStoryTopicProducer().sendMessage(message);
	}

}
