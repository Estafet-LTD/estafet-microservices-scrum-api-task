package com.estafet.microservices.api.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.task.dao.TaskDAO;
import com.estafet.microservices.api.task.model.Task;

@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Transactional(readOnly = true)
	public Task getTask(int taskId) {
		return taskDAO.getTask(taskId);
	}
	
	@Transactional(readOnly = true)
	public List<Task> getStoryTasks(int storyId) {
		return taskDAO.getStoryTasks(storyId);
	}

	@Transactional
	public Task createTask(int storyId, Task task) {
		task.setStoryId(storyId);
		return taskDAO.createTask(task);
	}

	@Transactional
	public Task updateTask(Task updated) {
		Task task = taskDAO.getTask(updated.getId());
		return taskDAO.updateTask(task.update(updated));
	}

	@Transactional
	public Task claimTask(int taskId) {
		return updateTask(getTask(taskId).claim());
	}

	@Transactional
	public Task updateRemainingTaskHours(Task updated) {
		Task task = taskDAO.getTask(updated.getId());
		task.setRemainingHours(updated.getRemainingHours(), updated.getRemainingUpdated());
		return taskDAO.updateTask(task);
	}

	@Transactional
	public Task completeTask(int taskId, String remainingUpdated) {
		Task task = taskDAO.getTask(taskId);
		task.complete(remainingUpdated);
		return taskDAO.updateTask(task);
	}

}
