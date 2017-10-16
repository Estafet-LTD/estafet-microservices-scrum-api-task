package com.estafet.microservices.api.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.task.model.Task;
import com.estafet.microservices.api.task.service.TaskService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping("/api")
	public Task getAPI() {
		return Task.getAPI();
	}
	
	@GetMapping("/task/{id}")
	public Task getTask(@PathVariable int id) {
		return taskService.getTask(id);
	}
	
	@GetMapping("/story/{id}/tasks")
	public List<Task> getStoryTasks(@PathVariable int id) {
		return new ArrayList<Task>(taskService.getStoryTasks(id));
	}
	
	@PostMapping("/story/{id}/task")
	public ResponseEntity createTask(@PathVariable int id, @RequestBody Task task) {
		return new ResponseEntity(taskService.createTask(id, task), HttpStatus.OK);
	}
	
	@PutMapping("/task/{id}/remainingHours")
	public ResponseEntity updateTaskRemainingHours(@PathVariable int id, @RequestBody Task task) {
		return new ResponseEntity(taskService.updateRemainingTaskHours(id, task), HttpStatus.OK);
	}
	
	@PostMapping("/task/{id}/complete")
	public ResponseEntity completeTask(@PathVariable int id, @RequestBody String remainingUpdated) {
		return new ResponseEntity(taskService.completeTask(id, remainingUpdated), HttpStatus.OK);
	}

	@PostMapping("/task/{id}/claim")
	public ResponseEntity claimTask(@PathVariable int id) {
		return new ResponseEntity(taskService.claimTask(id), HttpStatus.OK);
	}
	
}
