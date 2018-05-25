package com.estafet.microservices.api.task.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.task.model.Sprint;
import com.estafet.microservices.api.task.model.Story;

@Repository
public class StoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public Story getStory(int storyId) {
		return entityManager.find(Story.class, new Integer(storyId));
	}

	public Story createStory(Story story) {
		entityManager.persist(story);
		return story;
	}

	public Story updateStory(Story story) {
		if (story.getSprintId() != null) {
			Sprint sprint = entityManager.find(Sprint.class, new Integer(story.getSprintId()));
			story.setStorySprint(sprint);
		}
		entityManager.merge(story);
		return story;
	}

}
