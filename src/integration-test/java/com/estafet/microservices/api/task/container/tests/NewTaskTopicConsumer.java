package com.estafet.microservices.api.task.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicConsumer;

public class NewTaskTopicConsumer extends TopicConsumer {

	public NewTaskTopicConsumer() {
		super("new.task.topic");
	}

}
