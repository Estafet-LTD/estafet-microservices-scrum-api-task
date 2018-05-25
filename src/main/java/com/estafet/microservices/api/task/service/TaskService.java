package com.estafet.microservices.api.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.task.dao.SprintDAO;
import com.estafet.microservices.api.task.dao.StoryDAO;
import com.estafet.microservices.api.task.dao.TaskDAO;
import com.estafet.microservices.api.task.model.Sprint;
import com.estafet.microservices.api.task.model.Story;
import com.estafet.microservices.api.task.model.Task;

@Service
public class TaskService {
		
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private StoryDAO storyDAO;
	
	@Autowired
	private SprintDAO sprintDAO;

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
		Story story = storyDAO.getStory(storyId);
		if (!story.getStatus().equals("Completed")) {
			return taskDAO.createTask(task.init().setTaskStory(story));	
		}
		throw new RuntimeException("cannot add task to completed story");
	}

	@Transactional
	public Task updateTask(Task updated) {
		Task task = taskDAO.getTask(updated.getId());
		return taskDAO.updateTask(task.update(updated));
	}

	@Transactional
	public Task claimTask(int taskId) {
		Task task = getTask(taskId);
		String firstDay = task.getStory().getStorySprint().getFirstSprintDay();
		return updateTask(task.claim(firstDay));
	}

	@Transactional
	public Task updateRemainingTaskHours(int taskId, Task updated) {
		Task task = taskDAO.getTask(taskId);
		task.setRemainingHours(updated.getRemainingHours(), updated.getRemainingUpdated());
		return taskDAO.updateTask(task);
	}

	@Transactional
	public Task completeTask(int taskId, String remainingUpdated) {
		Task task = taskDAO.getTask(taskId);
		task.complete(remainingUpdated);
		return taskDAO.updateTask(task);
	}

	@Transactional
	public void newStory(Story story) {
		storyDAO.createStory(story);
	}

	@Transactional
	public void updateTask(Story story) {
		storyDAO.updateStory(story);
	}

	@Transactional
	public void newSprint(Sprint sprint) {
		sprintDAO.createSprint(sprint);
	}

}
