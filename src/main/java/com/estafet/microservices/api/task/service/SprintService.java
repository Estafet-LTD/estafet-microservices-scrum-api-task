package com.estafet.microservices.api.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintService {

	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	public String getFirstSprintDay(int sprintId) {
		List<String> days = restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
		return days.get(0);
	}
	
}
