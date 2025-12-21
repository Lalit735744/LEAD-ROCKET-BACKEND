package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import com.leadrocket.backend.users.model.Role;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantRoleRepositoryImpl implements TenantRoleRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantRoleRepositoryImpl(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    private String col(String companyId) { return helper.rolesCollection(companyId); }

    @Override
    public Role save(String companyId, Role role) {
        return mongoTemplate.save(role, col(companyId));
    }

    @Override
    public Optional<Role> findById(String companyId, String id) {
        Role r = mongoTemplate.findById(id, Role.class, col(companyId));
        return Optional.ofNullable(r);
    }

    @Override
    public List<Role> findAll(String companyId) {
        return mongoTemplate.findAll(Role.class, col(companyId));
    }
}

