package com.estafet.microservices.api.task.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testNewTask() {
		assertEquals("Not Started", new Task().getStatus());
	}

	@Test
	public void testCompleteFromNew() {
		assertEquals("Completed", new Task().complete("").getStatus());
	}

	@Test
	public void testCompleteFromInProgress() {
		assertEquals("Completed", new Task().claim("").complete("").getStatus());
	}

	@Test
	public void testReopen() {
		assertEquals("Not Started", new Task().complete("").reopen().getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testReopenFailed() {
		new Task().reopen().getStatus();
	}

	@Test
	public void testClaim() {
		assertEquals("In Progress", new Task().claim("").getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testClaimFailed() {
		assertEquals("In Progress", new Task().complete("").claim("").getStatus());
	}

	@Test
	public void testUpdateTime() {
		assertEquals(5, new Task().claim("").setRemainingHours(5, "").getRemainingHours().intValue());
	}

	@Test
	public void testUpdateTimeClose() {
		assertEquals("Completed", new Task().claim("").setRemainingHours(0, "").getStatus());
	}

}
