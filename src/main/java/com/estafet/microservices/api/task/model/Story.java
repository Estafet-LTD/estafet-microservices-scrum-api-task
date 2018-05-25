package com.estafet.microservices.api.task.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "STORY")
public class Story {

	@Id
	@Column(name = "STORY_ID")
	private Integer id;

	@Transient
	private Integer sprintId;

	@Column(name = "STATUS", nullable = false)
	private String status = "Not Started";

	@JsonIgnore
	@OneToMany(mappedBy = "taskStory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Task> tasks = new HashSet<Task>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", nullable = true, referencedColumnName = "SPRINT_ID", foreignKey = @ForeignKey(name = "STORY_TO_SPRINT_FK"))
	private Sprint storySprint;

	public Story setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public String getStatus() {
		return status;
	}

	@JsonIgnore
	public Sprint getStorySprint() {
		return storySprint;
	}

	public void setStorySprint(Sprint storySprint) {
		this.storySprint = storySprint;
	}

	public static Story fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Story.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
