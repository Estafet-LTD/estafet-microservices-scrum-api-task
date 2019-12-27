package com.estafet.microservices.api.task.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicConsumer;

public class UpdatedTaskTopicConsumer extends TopicConsumer {

	public UpdatedTaskTopicConsumer() {
		super("update.task.topic");
	}

}
