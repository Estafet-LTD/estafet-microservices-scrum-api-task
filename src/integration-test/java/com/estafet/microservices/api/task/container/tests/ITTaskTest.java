package com.estafet.microservices.api.task.container.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.estafet.microservices.api.task.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ITTaskTest {

	@Before
	public void before() throws Exception {
		RestAssured.baseURI = System.getenv("TASK_API_SERVICE_URI");
	}

	@Test
	public void testGetAPI() {
		get("/api").then()
			.body("id", is(1))
			.body("title", is("this is a task"))
			.body("initialHours", is(12))
			.body("remainingHours", is(0))
			.body("status", is("Completed"))
			.body("storyId", is(1))
			.body("remainingUpdated", is("2017-10-06 00:00:00"));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testGetTask() {
		get("/task/1000").then()
			.body("id", is(1000))
			.body("title", is("Task #1"))
			.body("description", is("Task #1"))
			.body("initialHours", is(12))
			.body("remainingHours", is(0))
			.body("status", is("Not Started"))
			.body("storyId", is(1000));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testGetStoryTasks() {
		get("/story/1000/tasks").then()
			.body("task.id", hasItems(1000, 1001))
			.body("task.title", hasItems("Task #1", "Task #2"))
			.body("task.description", hasItems("Task #1", "Task #2"))
			.body("task.initialHours", hasItems(13, 20))
			.body("task.remainingHours", hasItems(13, 20))
			.body("task.status", hasItems("Not Started", "Not Started"))
			.body("task.storyId", hasItems(1000, 1000));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testCreateTask() throws Exception {
		given().contentType(ContentType.JSON)
			.body("{\"title\":\"Task #3\",\"description\":\"Task #3\",\"initialHours\":5}")
			.when()
				.post("/story/1000/task")
			.then()
				.statusCode(HttpURLConnection.HTTP_OK)
				.body("id", is(1))
				.body("title", is("Task #3"))
				.body("description", is("Task #3"))
				.body("initialHours", is(5))
				.body("remainingHours", is(5))
				.body("status", is("Not Started"))
				.body("storyId", is(1000));
		
		get("/task/1").then()
			.body("id", is(1))
			.body("title", is("Task #3"))
			.body("description", is("Task #3"))
			.body("initialHours", is(5))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"))
			.body("storyId", is(1000));
		
		Task task = new ObjectMapper().readValue(NewTaskTopic.consume(), Task.class);
		assertThat(task.getId(), is(1));
		assertThat(task.getTitle(), is("Task #1"));
		assertThat(task.getDescription(), is("Task #1"));
		assertThat(task.getInitialHours(), is(5));
		assertThat(task.getRemainingHours(), is(5));
		assertThat(task.getStatus(), is("Not Started"));
		assertThat(task.getStoryId(), is(1000));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testUpdateTaskRemainingHours() {
		fail("Not yet implemented");
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testCompleteTask() {
		fail("Not yet implemented");
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testClaimTask() {
		fail("Not yet implemented");
	}

}
