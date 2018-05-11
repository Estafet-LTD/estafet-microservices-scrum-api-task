package com.estafet.microservices.api.task.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.task.model.Story;
import com.estafet.microservices.api.task.service.TaskService;

import io.opentracing.Tracer;

@Component
public class NewStoryConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private TaskService taskService;

	@JmsListener(destination = "new.story.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		try {
			taskService.newStory(Story.fromJSON(message));
		} finally {
			tracer.activeSpan().close();
		}
	}

}
