package com.estafet.microservices.api.task.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TASK")
public class Task {

	@Id
	@SequenceGenerator(name = "TASK_ID_SEQ", sequenceName = "TASK_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_ID_SEQ")
	@Column(name = "TASK_ID")
	private Integer id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "INITIAL_HOURS", nullable = false)
	private Integer initialHours;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "REMAINING_HOURS", nullable = false)
	private Integer remainingHours;

	@Column(name = "STATUS", nullable = false)
	private String status = "Not Started";

	@JsonInclude(Include.NON_NULL)
	@Column(name = "REMAINING_UPDATED")
	private String remainingUpdated;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STORY_ID", nullable = false, referencedColumnName = "STORY_ID", foreignKey = @ForeignKey(name = "TASK_TO_STORY_FK"))
	private Story taskStory;
	
	public Task init() {
		this.remainingHours = initialHours;
		return this;
	}
	
	public Task setTaskStory(Story taskStory) {
		this.taskStory = taskStory;
		return this;
	}

	public Task complete(String remainingUpdated) {
		if (!"Completed".equals(status)) {
			this.remainingHours = 0;
			this.status = "Completed";
			this.remainingUpdated = remainingUpdated;
			return this;
		}
		throw new RuntimeException("Task is already complete.");
	}

	public Task reopen() {
		if ("Completed".equals(status)) {
			status = "Not Started";
			return this;
		}
		throw new RuntimeException("Task must be completed to reopen.");
	}

	public Task claim(String remainingUpdated) {
		if ("Not Started".equals(status)) {
			this.status = "In Progress";
			this.remainingUpdated = remainingUpdated;
			return this;
		}
		throw new RuntimeException("Task needs to be not started to claim it.");
	}

	public Task setRemainingHours(Integer remainingHours, String remainingUpdated) {
		if (remainingHours != null) {
			this.remainingHours = remainingHours;
			this.remainingUpdated = remainingUpdated;
			if (remainingHours == 0) {
				status = "Completed";
			}
		}
		return this;
	}

	public Task update(Task newTask) {
		title = newTask.getTitle() != null ? newTask.getTitle() : title;
		description = newTask.getDescription() != null ? newTask.getDescription() : description;
		initialHours = newTask.getInitialHours() != null ? newTask.getInitialHours() : initialHours;
		status = newTask.getStatus() != null ? newTask.getStatus() : status;
		return this;
	}
	
	@JsonIgnore
	public Story getStory() {
		return taskStory;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Integer getInitialHours() {
		return initialHours;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getRemainingUpdated() {
		return remainingUpdated;
	}

	public Task setRemainingUpdated(String remainingUpdated) {
		this.remainingUpdated = remainingUpdated;
		return this;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Task setInitialHours(Integer initialHours) {
		this.initialHours = initialHours;
		return this;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public Task setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Integer getStoryId() {
		return taskStory.getId();
	}

	public static Task fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Task.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toJSON() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Task getAPI() {
		Task task = new Task();
		task.id = 1;
		task.title = "my task";
		task.description = "my task description";
		task.initialHours = 3;
		task.setTaskStory(new Story().setId(1));
		task.remainingHours = 3;
		task.remainingUpdated = "2017-10-16 00:00:00";
		task.status = "Not Started";
		return task;
	}

}
