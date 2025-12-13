package com.leadrocket.backend.teams.repository;

import com.leadrocket.backend.teams.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
}
