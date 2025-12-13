package com.leadrocket.backend.teams.controller;

import com.leadrocket.backend.teams.model.Team;
import com.leadrocket.backend.teams.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

	private final TeamService service;

	public TeamController(TeamService service) {
		this.service = service;
	}

	@PostMapping
	public Team create(@RequestBody Team team) {
		return service.create(team);
	}

	@GetMapping
	public List<Team> getAll() {
		return service.getAll();
	}
}
