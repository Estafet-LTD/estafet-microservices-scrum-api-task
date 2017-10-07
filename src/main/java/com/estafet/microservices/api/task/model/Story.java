package com.estafet.microservices.api.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	private Integer id;

	private Integer sprintId;

	private String status;

	public Integer getId() {
		return id;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public String getStatus() {
		return status;
	}

}
