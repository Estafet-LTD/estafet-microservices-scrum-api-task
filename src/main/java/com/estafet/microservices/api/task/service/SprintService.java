package com.estafet.microservices.api.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintService {

	@SuppressWarnings("unchecked")
	public String getFirstSprintDay(int sprintId) {
		List<String> days = new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
		return days.get(0);
	}
	
}
