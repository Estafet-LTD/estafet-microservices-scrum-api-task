package com.estafet.microservices.api.task.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "STORY")
public class Story {

	@Id
	@Column(name = "STORY_ID")
	private Integer id;

	@Column(name = "SPRINT_ID")
	private Integer sprintId;

	@Column(name = "STATUS", nullable = false)
	private String status = "Not Started";
	
	@OneToMany(mappedBy = "taskStory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Task> tasks = new HashSet<Task>();

	public Integer getId() {
		return id;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public String getStatus() {
		return status;
	}
	
	public static Story fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Story.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
