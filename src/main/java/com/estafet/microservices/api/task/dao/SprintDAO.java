package com.estafet.microservices.api.task.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.task.model.Sprint;

@Repository
public class SprintDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public Sprint createSprint(Sprint sprint) {
		entityManager.persist(sprint);
		return sprint;
	}

}
