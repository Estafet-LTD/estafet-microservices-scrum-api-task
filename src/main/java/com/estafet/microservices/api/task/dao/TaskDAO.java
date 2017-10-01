package com.estafet.microservices.api.task.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.task.jms.NewTaskProducer;
import com.estafet.microservices.api.task.jms.UpdateTaskProducer;
import com.estafet.microservices.api.task.model.Task;

@Repository
public class TaskDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UpdateTaskProducer updateTaskProducer;

	@Autowired
	private NewTaskProducer newTaskProducer;

	public Task getTask(int taskId) {
		return entityManager.find(Task.class, new Integer(taskId));
	}

	public List<Task> getStoryTasks(int storyId) {
		return entityManager.createQuery("select t from Task t where t.storyId = " + storyId, Task.class)
				.getResultList();
	}

	public Task createTask(Task task) {
		entityManager.persist(task);
		newTaskProducer.sendMessage(task);
		return task;
	}

	public Task updateTask(Task task) {
		entityManager.merge(task);
		updateTaskProducer.sendMessage(task);
		return task;
	}

}
