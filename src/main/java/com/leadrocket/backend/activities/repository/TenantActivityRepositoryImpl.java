// MongoTemplate based implementation for tenant activity repository

package com.leadrocket.backend.activities.repository;

import com.leadrocket.backend.activities.model.Activity;
import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TenantActivityRepositoryImpl implements TenantActivityRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantActivityRepositoryImpl(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public Activity save(String companyId, Activity activity) {
        return mongoTemplate.save(activity, helper.activitiesCollection(companyId));
    }

    @Override
    public Optional<Activity> findById(String companyId, String id) {
        return Optional.ofNullable(
                mongoTemplate.findById(id, Activity.class, helper.activitiesCollection(companyId))
        );
    }

    @Override
    public List<Activity> findByLead(String companyId, String leadId) {
        Query q = new Query(Criteria.where("leadId").is(leadId));
        return mongoTemplate.find(q, Activity.class, helper.activitiesCollection(companyId));
    }

    @Override
    public List<Activity> findByUser(String companyId, String userId) {
        Query q = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(q, Activity.class, helper.activitiesCollection(companyId));
    }

    @Override
    public List<Activity> findPendingByUser(String companyId, String userId) {
        Query q = new Query(
                Criteria.where("userId").is(userId)
                        .and("status").ne("DONE")
        );
        return mongoTemplate.find(q, Activity.class, helper.activitiesCollection(companyId));
    }

    @Override
    public List<Activity> findOverdueByUser(String companyId, String userId) {
        Query q = new Query(
                Criteria.where("userId").is(userId)
                        .and("status").ne("DONE")
                        .and("followUpDate").lt(new Date())
        );
        return mongoTemplate.find(q, Activity.class, helper.activitiesCollection(companyId));
    }
}
