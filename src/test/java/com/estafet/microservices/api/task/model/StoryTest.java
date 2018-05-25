package com.estafet.microservices.api.task.model;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.Test;

public class StoryTest {

	@Test
	public void testFromJSON() {
		Story story = Story.fromJSON("{\"id\":2000,\"status\":\"Not Started\", \"sprintId\":1}");
		assertThat(story.getSprintId(), is(story.getSprintId()));
	}

}
