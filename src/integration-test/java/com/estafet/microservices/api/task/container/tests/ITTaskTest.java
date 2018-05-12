package com.estafet.microservices.api.task.container.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

	NewTaskTopicConsumer newTaskTopicConsumer;
	UpdatedTaskTopicConsumer updatedTaskTopicConsumer;
	
	@Before
	public void before() {
		RestAssured.baseURI = System.getenv("TASK_API_SERVICE_URI");
		newTaskTopicConsumer = new NewTaskTopicConsumer();
		updatedTaskTopicConsumer = new UpdatedTaskTopicConsumer();
	}
	
	@After
	public void after() {
		newTaskTopicConsumer.closeConnection();
		updatedTaskTopicConsumer.closeConnection();
	}

	@Test
	public void testGetAPI() {
		get("/api").then()
			.body("id", is(1))
			.body("title", is("my task"))
			.body("initialHours", is(3))
			.body("remainingHours", is(3))
			.body("status", is("Not Started"))
			.body("remainingUpdated", is("2017-10-16 00:00:00"));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testGetTask() {
		get("/task/1000").then()
			.body("id", is(1000))
			.body("title", is("Task #1"))
			.body("description", is("Task #1"))
			.body("initialHours", is(13))
			.body("remainingHours", is(13))
			.body("status", is("Not Started"));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testGetStoryTasks() {
		get("/story/1000/tasks").then()
			.body("id", hasItems(1000, 1001))
			.body("title", hasItems("Task #1", "Task #2"))
			.body("description", hasItems("Task #1", "Task #2"))
			.body("initialHours", hasItems(13, 20))
			.body("remainingHours", hasItems(13, 20))
			.body("status", hasItems("Not Started", "Not Started"));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testCreateTask() throws Exception {
		given()
			.contentType(ContentType.JSON)
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
				.body("status", is("Not Started"));
		
		get("/task/1").then()
			.body("id", is(1))
			.body("title", is("Task #3"))
			.body("description", is("Task #3"))
			.body("initialHours", is(5))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));
		
		Task task = new ObjectMapper().readValue(newTaskTopicConsumer.consumeMessage(), Task.class);
		assertThat(task.getId(), is(1));
		assertThat(task.getTitle(), is("Task #3"));
		assertThat(task.getDescription(), is("Task #3"));
		assertThat(task.getInitialHours(), is(5));
		assertThat(task.getRemainingHours(), is(5));
		assertThat(task.getStatus(), is("Not Started"));
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testUpdateTaskRemainingHours() throws Exception {
		given()
			.contentType(ContentType.JSON)
			.body("{\"remainingHours\":5}")
		.when()
			.put("/task/1001/remainingHours")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(5));
		
		get("/task/1001").then()
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));
	
		Task task = new ObjectMapper().readValue(updatedTaskTopicConsumer.consumeMessage(), Task.class);
		assertThat(task.getId(), is(1001));
		assertThat(task.getTitle(), is("Task #2"));
		assertThat(task.getDescription(), is("Task #2"));
		assertThat(task.getInitialHours(), is(20));
		assertThat(task.getRemainingHours(), is(5));
		assertThat(task.getStatus(), is("Not Started"));	
	}
	
	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testUpdateTaskRemainingHoursComplete() throws Exception {
		given()
			.contentType(ContentType.JSON)
			.body("{\"remainingHours\":0}")
		.when()
			.put("/task/1001/remainingHours")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(0))
			.body("status", is("Completed"));
		
		get("/task/1001").then()
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(0))
			.body("status", is("Completed"));

		Task task = new ObjectMapper().readValue(updatedTaskTopicConsumer.consumeMessage(), Task.class);
		assertThat(task.getId(), is(1001));
		assertThat(task.getTitle(), is("Task #2"));
		assertThat(task.getDescription(), is("Task #2"));
		assertThat(task.getInitialHours(), is(20));
		assertThat(task.getRemainingHours(), is(0));
		assertThat(task.getStatus(), is("Completed"));			
	}

	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testCompleteTask() throws Exception {
		given()
			.contentType(ContentType.JSON)
			.body("{\"remainingUpdated\":\"2017-10-16 00:00:00\"}")
		.when()
			.post("/task/1001/complete")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(0))
			.body("status", is("Completed"));
	
		Task task = new ObjectMapper().readValue(updatedTaskTopicConsumer.consumeMessage(), Task.class);
		assertThat(task.getId(), is(1001));
		assertThat(task.getTitle(), is("Task #2"));
		assertThat(task.getDescription(), is("Task #2"));
		assertThat(task.getInitialHours(), is(20));
		assertThat(task.getRemainingHours(), is(0));
		assertThat(task.getStatus(), is("Completed"));
	}

	@Ignore
	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testClaimTask() throws Exception {
		when()
			.post("/task/1001/claim")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(20))
			.body("status", is("In Progress"));

		Task task = new ObjectMapper().readValue(updatedTaskTopicConsumer.consumeMessage(), Task.class);
		assertThat(task.getId(), is(1001));
		assertThat(task.getTitle(), is("Task #2"));
		assertThat(task.getDescription(), is("Task #2"));
		assertThat(task.getInitialHours(), is(20));
		assertThat(task.getRemainingHours(), is(20));
		assertThat(task.getStatus(), is("In Progress"));
	}
	
	@Test
	@DatabaseSetup("ITTaskTest-data.xml")
	public void testNewStoryConsumer() {
		NewStoryTopicProducer.send("{\"id\":2000,\"status\":\"Not Started\"}");
		given()
			.contentType(ContentType.JSON)
			.body("{\"title\":\"Task #5\",\"description\":\"Task #5\",\"initialHours\":5}")
		.when()
			.post("/story/2000/task")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(2))
			.body("title", is("Task #5"))
			.body("description", is("Task #5"))
			.body("initialHours", is(5))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));
	}

}
