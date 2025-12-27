// Business logic for team management

package com.leadrocket.backend.teams.service;

import com.leadrocket.backend.common.exception.NotFoundException;
import com.leadrocket.backend.teams.dto.TeamRequestDTO;
import com.leadrocket.backend.teams.dto.TeamResponseDTO;
import com.leadrocket.backend.teams.model.Team;
import com.leadrocket.backend.teams.repository.TenantTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

	private final TenantTeamRepository repository;

	public TeamService(TenantTeamRepository repository) {
		this.repository = repository;
	}

	public TeamResponseDTO create(String companyId, TeamRequestDTO dto) {

		Team team = new Team();
		team.setCompanyId(companyId);
		team.setName(dto.getName());
		team.setManagerId(dto.getManagerId());
		team.setMemberIds(dto.getMemberIds());

		return toDTO(repository.save(companyId, team));
	}

	public List<TeamResponseDTO> list(String companyId) {
		return repository.findAll(companyId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public TeamResponseDTO getById(String companyId, String teamId) {
		Team team = repository.findById(companyId, teamId)
				.orElseThrow(() -> new NotFoundException("Team not found"));
		return toDTO(team);
	}

	private TeamResponseDTO toDTO(Team team) {
		TeamResponseDTO dto = new TeamResponseDTO();
		dto.setId(team.getId());
		dto.setName(team.getName());
		dto.setManagerId(team.getManagerId());
		dto.setMemberIds(team.getMemberIds());
		return dto;
	}
}
