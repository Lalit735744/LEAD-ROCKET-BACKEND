package com.leadrocket.backend.teams.service;

import com.leadrocket.backend.teams.model.Team;
import com.leadrocket.backend.teams.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

	private final TeamRepository repository;

	public TeamService(TeamRepository repository) {
		this.repository = repository;
	}

	public Team create(Team team) {
		return repository.save(team);
	}

	public List<Team> getAll() {
		return repository.findAll();
	}
}
