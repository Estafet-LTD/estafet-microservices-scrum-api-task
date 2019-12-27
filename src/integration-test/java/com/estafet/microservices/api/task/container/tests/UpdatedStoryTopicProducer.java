package com.estafet.microservices.api.task.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicProducer;

public class UpdatedStoryTopicProducer extends TopicProducer {

	public UpdatedStoryTopicProducer() {
		super("update.task.topic");
	}
	
	public static void send(String message) {
		new UpdatedStoryTopicProducer().sendMessage(message);
	}

}
