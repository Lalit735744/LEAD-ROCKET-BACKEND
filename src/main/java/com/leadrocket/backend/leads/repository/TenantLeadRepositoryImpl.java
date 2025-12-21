package com.leadrocket.backend.leads.repository;

import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantLeadRepositoryImpl implements TenantLeadRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantLeadRepositoryImpl(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public Lead save(String companyId, Lead lead) {
        return mongoTemplate.save(lead, helper.leadsCollection(companyId));
    }

    @Override
    public Optional<Lead> findById(String companyId, String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Lead.class, helper.leadsCollection(companyId)));
    }

    @Override
    public List<Lead> findByAssignedTo(String companyId, String userId) {
        Query q = new Query(Criteria.where("assignedTo").is(userId));
        return mongoTemplate.find(q, Lead.class, helper.leadsCollection(companyId));
    }
}

