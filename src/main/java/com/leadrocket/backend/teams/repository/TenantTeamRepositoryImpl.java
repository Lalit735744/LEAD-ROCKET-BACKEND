// MongoTemplate implementation for tenant teams

package com.leadrocket.backend.teams.repository;

import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import com.leadrocket.backend.teams.model.Team;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantTeamRepositoryImpl implements TenantTeamRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantTeamRepositoryImpl(MongoTemplate mongoTemplate,
                                    TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public Team save(String companyId, Team team) {
        return mongoTemplate.save(team, helper.teamsCollection(companyId));
    }

    @Override
    public Optional<Team> findById(String companyId, String teamId) {
        return Optional.ofNullable(
                mongoTemplate.findById(
                        teamId,
                        Team.class,
                        helper.teamsCollection(companyId)
                )
        );
    }

    @Override
    public List<Team> findAll(String companyId) {
        return mongoTemplate.findAll(
                Team.class,
                helper.teamsCollection(companyId)
        );
    }
}
